package com.tsingtec.follow.service.mini;

import com.tsingtec.follow.entity.mini.Check;
import com.tsingtec.follow.entity.mini.Doctor;
import com.tsingtec.follow.entity.mini.Information;
import com.tsingtec.follow.exception.BusinessException;
import com.tsingtec.follow.exception.code.BaseExceptionType;
import com.tsingtec.follow.repository.mini.CheckRepository;
import com.tsingtec.follow.repository.mini.InformationRepository;
import com.tsingtec.follow.utils.BeanMapper;
import com.tsingtec.follow.utils.BeanUtils;
import com.tsingtec.follow.vo.req.information.InformationAddReqVO;
import com.tsingtec.follow.vo.req.information.InformationPageReqVO;
import com.tsingtec.follow.vo.req.information.InformationUpdateReqVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lj
 * @Date 2021/5/26 15:30
 * @Version 1.0
 */
@Service
public class InformationService {

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private CheckRepository checkRepository;

    public Information findById(Integer id){
        Information information = informationRepository.findById(id).orElse(new Information());
        List<Check> checks = checkRepository.findAll();
        if(information!=null && information.getExamination()!=null){
            List<String> enNames = information.getExamination().stream().map(examination -> examination.getEnName()).collect(Collectors.toList());
            checks.forEach(check -> {
                if(enNames.indexOf(check.getEnName())>-1){
                    check.setChecked(true);
                }
            });
        }
        information.setChecks(checks);
        return information;
    }

    @Transactional
    public void save(Information information){
        informationRepository.save(information);
    }

    public Information findByUid(Integer uid) {
        return informationRepository.findByMaUser_Id(uid);
    }


    public Information findByPhone(String phone) {
        return informationRepository.findByPhone(phone);
    }


    public List<Information> getByDid(Integer did){
        return informationRepository.getByDoctor_idOrderByExaminationAsc(did);
    }

    public Page<Information> findAll(InformationPageReqVO vo) {
        Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(), Sort.Direction.DESC,"id");
        return informationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            //查询and
            List<Predicate> listAnd = new ArrayList<Predicate>();

            Join<Object, Doctor> doctor = root.join("doctor", JoinType.LEFT);
            if (null!=vo.getDid()){
                listAnd.add(criteriaBuilder.equal(doctor.get("id"),vo.getDid()));
            }

            if (StringUtils.isNotBlank(vo.getRecordNo())){
                listAnd.add(criteriaBuilder.like(root.get("recordNo"),vo.getRecordNo()+"%"));
            }

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
    public void insert(InformationAddReqVO vo) {
        if(findByPhone(vo.getPhone())!=null) throw  new BusinessException(BaseExceptionType.USER_ERROR,"已存在该号码的用户,请查证后再试!");
        Information information = new Information();
        BeanMapper.mapExcludeNull(vo,information);
        informationRepository.save(information);
    }

    @Transactional
    public void update(InformationUpdateReqVO vo) {
        Information info = findByPhone(vo.getPhone());
        if(!info.getId().equals(vo.getId()))  throw  new BusinessException(BaseExceptionType.USER_ERROR,"已存在该号码的用户,请查证后再试!");
        Information information = informationRepository.getOne(vo.getId());
        BeanUtils.copyPropertiesIgnoreNull(vo,information);
        informationRepository.save(information);
    }

    @Transactional
    public void delete(List<Integer> ids) {
        informationRepository.deleteBatch(ids);
    }
}
