package com.tsingtec.follow.service.mini;

import com.google.common.collect.Lists;
import com.tsingtec.follow.entity.mini.Doctor;
import com.tsingtec.follow.entity.sys.Admin;
import com.tsingtec.follow.exception.BusinessException;
import com.tsingtec.follow.exception.code.BaseExceptionType;
import com.tsingtec.follow.repository.mini.DoctorRepository;
import com.tsingtec.follow.service.sys.AdminService;
import com.tsingtec.follow.utils.BeanMapper;
import com.tsingtec.follow.vo.req.doctor.DoctorAddReqVO;
import com.tsingtec.follow.vo.req.doctor.DoctorPageReqVO;
import com.tsingtec.follow.vo.req.doctor.DoctorUpdateReqVO;
import com.tsingtec.follow.vo.req.sys.admin.AdminAddReqVO;
import com.tsingtec.follow.vo.req.sys.admin.AdminUpdateReqVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/5/28 17:28
 * @Version 1.0
 */
@Service
public class DoctorService {

    @Autowired
    private AdminService adminService;


    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor get(Integer id){
        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor findByUid(Integer uid){
        return doctorRepository.findByMaUser_Id(uid);
    }

    public Doctor findByPhone(String phone){
        return doctorRepository.findByPhone(phone);
    }

    /**
     * 小程序端使用
     * @param doctor
     */
    @Transactional
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }


    public Page<Doctor> findAll(DoctorPageReqVO vo) {
        Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(), Sort.Direction.DESC,"id");
        return doctorRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            //查询and
            List<Predicate> listAnd = new ArrayList<Predicate>();

            Predicate[] array = new Predicate[listAnd.size()];
            Predicate Pre_And = criteriaBuilder.and(listAnd.toArray(array));
            List<Predicate> listOr = new ArrayList<Predicate>();
            //查询or 模糊查询前面相似
            if(StringUtils.isNotBlank(vo.getTitle())) {
                listOr.add(criteriaBuilder.like(root.get("name"), vo.getTitle()+"%"));
                listOr.add(criteriaBuilder.like(root.get("phone"), vo.getTitle()+"%"));
                Predicate[] arrayOr = new Predicate[listOr.size()];
                Predicate Pre_Or = criteriaBuilder.or(listOr.toArray(arrayOr));
                return criteriaQuery.where(Pre_And,Pre_Or).getRestriction();
            }
            return criteriaQuery.where(listAnd.toArray(new Predicate[listAnd.size()])).getRestriction();
        },pageable);
    }



    @Transactional
    public void insert(DoctorAddReqVO vo){

        Doctor doctor = findByPhone(vo.getPhone());

        if(null!=doctor){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"已存在相同手机号的医生信息,请修改后再保存");
        }

        doctor = new Doctor();
        BeanMapper.mapExcludeNull(vo,doctor);

        doctorRepository.save(doctor);

        AdminAddReqVO admin = new AdminAddReqVO();
        admin.setName(doctor.getName());
        admin.setLoginName(doctor.getPhone());
        admin.setPassword(StringUtils.substring(vo.getPhone(),5));
        admin.setStatus((short)0);
        adminService.insert(admin);
    }

    @Transactional
    public void update(DoctorUpdateReqVO vo){

        Doctor doctor = findByPhone(vo.getPhone());

        if(doctor!=null && !vo.getId().equals(doctor.getId())){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"已存在相同手机号的医生信息,请修改后再保存");
        }

        doctor = get(vo.getId());

        Admin admin = adminService.findByLoginName(doctor.getPhone());

        BeanMapper.mapExcludeNull(vo,doctor);
        doctorRepository.save(doctor);

        AdminUpdateReqVO adminUpdateReqVO = new AdminUpdateReqVO();
        adminUpdateReqVO.setId(admin.getId());
        adminUpdateReqVO.setName(vo.getName());
        adminUpdateReqVO.setLoginName(vo.getPhone());
        adminUpdateReqVO.setPassword(StringUtils.substring(vo.getPhone(),5));
        adminUpdateReqVO.setStatus((short)0);
        adminService.update(adminUpdateReqVO);
    }


    @Transactional
    public void delete(List<Integer> ids) {
        List<Doctor> doctors = doctorRepository.findAllById(ids);
        List<Integer> aids = Lists.newArrayList();
        doctors.forEach(doctor -> {
            Admin admin = adminService.findByLoginName(doctor.getPhone());
            if(admin!=null){
                aids.add(admin.getId());
            }
        });
        //联动删除账号
        adminService.deleteBatch(aids);
        doctorRepository.deleteInBatch(doctors);
    }

    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }
}
