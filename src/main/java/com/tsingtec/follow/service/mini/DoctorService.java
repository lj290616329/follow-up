package com.tsingtec.follow.service.mini;

import com.tsingtec.follow.entity.mini.Doctor;
import com.tsingtec.follow.entity.sys.Admin;
import com.tsingtec.follow.exception.BusinessException;
import com.tsingtec.follow.exception.code.BaseExceptionType;
import com.tsingtec.follow.repository.mini.DoctorRepository;
import com.tsingtec.follow.service.sys.AdminService;
import com.tsingtec.follow.utils.BeanMapper;
import com.tsingtec.follow.vo.req.doctor.DoctorAddReqVO;
import com.tsingtec.follow.vo.req.doctor.DoctorUpdateReqVO;
import com.tsingtec.follow.vo.req.sys.admin.AdminAddReqVO;
import com.tsingtec.follow.vo.req.sys.admin.AdminUpdateReqVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}
