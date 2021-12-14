package com.tsingtec.follow.service.mini;

import com.tsingtec.follow.entity.Examination;
import com.tsingtec.follow.entity.mini.Check;
import com.tsingtec.follow.entity.mini.Doctor;
import com.tsingtec.follow.entity.mini.Information;
import com.tsingtec.follow.entity.mini.ReviewPlan;
import com.tsingtec.follow.handler.annotation.AddReviewAnnotation;
import com.tsingtec.follow.repository.mini.CheckRepository;
import com.tsingtec.follow.repository.mini.InformationRepository;
import com.tsingtec.follow.repository.mini.ReviewPlanRepository;
import com.tsingtec.follow.utils.BeanMapper;
import com.tsingtec.follow.utils.BeanUtils;
import com.tsingtec.follow.vo.req.information.InformationPageReqVO;
import com.tsingtec.follow.vo.req.plan.ReviewPlanAddReqVO;
import com.tsingtec.follow.vo.req.plan.ReviewPlanExaminationReqVO;
import com.tsingtec.follow.vo.req.plan.ReviewPlanUpdateReqVO;
import com.tsingtec.follow.vo.resp.review.ReviewPlanRespVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lj
 * @Date 2021/5/26 15:30
 * @Version 1.0
 */
@Service
public class ReviewPlanService {

    @Autowired
    private ReviewPlanRepository reviewPlanRepository;

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private CheckRepository checkRepository;


    public ReviewPlan getById(Integer id){
        ReviewPlan reviewPlan = reviewPlanRepository.findById(id).orElse(null);;
        if(null!=reviewPlan && !reviewPlan.getExamine().isEmpty()){
            List<Check> checks = checkRepository.findByEnNameIn(reviewPlan.getExamine());
            reviewPlan.setChecks(checks);
            if(reviewPlan.getExamination()==null){
                reviewPlan.setExamination(BeanMapper.mapList(checks,Examination.class));
            }
        }
        return reviewPlan;
    }

    public ReviewPlan findCheckedById(Integer id){
        ReviewPlan reviewPlan = reviewPlanRepository.findById(id).orElse(new ReviewPlan());
        List<Check> checks = checkRepository.findAll();
        checks.forEach(check -> {
            if(reviewPlan.getExamine().indexOf(check.getEnName())>-1){
                check.setChecked(true);
            }
        });
        reviewPlan.setChecks(checks);

        reviewPlan.setCheckMap(checks.stream().collect(Collectors.groupingBy(Check::getCategory)));
        return  reviewPlan;
    }

    @Transactional
    public void save(ReviewPlan reviewPlan){
        reviewPlanRepository.save(reviewPlan);
    }


    @Transient
    public void save(ReviewPlanExaminationReqVO vo){
        ReviewPlan reviewPlan = reviewPlanRepository.getOne(vo.getId());
        BeanMapper.mapExcludeNull(vo,reviewPlan);
        reviewPlanRepository.save(reviewPlan);
    }


    public List<ReviewPlan> pageByIid(Integer iid){
        List<ReviewPlan> reviewPlans = reviewPlanRepository.findByInformation_IdOrderByReviewTimeDesc(iid);
        reviewPlans.forEach(reviewPlan -> {
            List<Check> checks = checkRepository.findByEnNameIn(reviewPlan.getExamine());
            reviewPlan.setChecks(checks);
        });
        return reviewPlans;
    }

    /**
     * 超过3个 折叠显示
     * @param iid
     * @return
     */
    public List<ReviewPlanRespVO> findByIid(Integer iid){
        List<ReviewPlan> reviewPlans = reviewPlanRepository.findByInformation_IdOrderByReviewTimeDesc(iid);
        ReviewPlan reviewPlan = reviewPlanRepository.getTopByInformation_IdAndExaminationIsNotNullAndReplyIsNotNullOrderByUpdateTimeDesc(iid);
        List<Integer> ins = Arrays.asList(0,1,2);
        if(reviewPlans.size()>3){
            Integer size = reviewPlans.size()-1;
            if(null != reviewPlan){
                Integer index = reviewPlans.indexOf(reviewPlan);
                //下次检查就是最后一个时或者第一个时,多补位一个,从而可以显示3个
                index = index==size ? index-1:index==0?1:index;

                ins = Arrays.asList(index+1,index,index-1);
            }
        }

        reviewPlans.forEach(r -> {
            if(!r.getExamine().isEmpty()){
                List<Check> checks = checkRepository.findByEnNameIn(r.getExamine());
                r.setChecks(checks);
            }
        });
        List<ReviewPlanRespVO> vos = BeanMapper.mapList(reviewPlans,ReviewPlanRespVO.class);
        for (int i = 0; i <vos.size() ; i++) {
            if(ins.indexOf(i)>-1){
                vos.get(i).setShow(true);
            }
        }
        return vos;
    }

    /**
     * 获取最近的一条待检测的计划
     * @param iid
     * @return
     */
    public ReviewPlan nearByIid(Integer iid){
        ReviewPlan reviewPlan = reviewPlanRepository.getTopByInformation_IdAndExaminationIsNullOrderByReviewTimeAsc(iid);
        List<Check> checks = checkRepository.findByEnNameIn(reviewPlan.getExamine());
        reviewPlan.setExamination(BeanMapper.mapList(checks, Examination.class));
        return reviewPlan;
    }

    /**
     * 获取最后一条被回复的记录
     * @param iid
     * @return
     */
    public ReviewPlan nearReplyByIid(Integer iid){
        ReviewPlan reviewPlan = reviewPlanRepository.getTopByInformation_IdAndExaminationIsNotNullAndReplyIsNotNullOrderByUpdateTimeDesc(iid);
        if(reviewPlan!=null && !reviewPlan.getExamine().isEmpty()){
            List<Check> checks = checkRepository.findByEnNameIn(reviewPlan.getExamine());
            reviewPlan.setExamination(BeanMapper.mapList(checks, Examination.class));
        }
        return reviewPlan;
    }


    /**
     * 医生分页获取对应账号下的所有查询条件的内容
     * 姓名或者病历号码 前相似
     * @param vo
     * @return
     */
    public Page<ReviewPlan> findAll(InformationPageReqVO vo) {
        Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(), Sort.Direction.DESC,"id");
        return reviewPlanRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            //查询and
            List<Predicate> listAnd = new ArrayList<Predicate>();

            Join<Object, Information> information = root.join("information", JoinType.LEFT);

            Join<Object, Doctor> doctor = information.join("doctor", JoinType.LEFT);

            if (null!=vo.getDid()){
                listAnd.add(criteriaBuilder.equal(doctor.get("id"),vo.getDid()));
            }

            listAnd.add(criteriaBuilder.isNotNull(root.get("examination")));

            Predicate[] array = new Predicate[listAnd.size()];
            Predicate Pre_And = criteriaBuilder.and(listAnd.toArray(array));
            List<Predicate> listOr = new ArrayList<Predicate>();
            //查询or 模糊查询前面相似
            if(StringUtils.isNotBlank(vo.getTitle())) {
                listOr.add(criteriaBuilder.like(information.get("name"), vo.getTitle()+"%"));
                listOr.add(criteriaBuilder.like(information.get("recordNo"), vo.getTitle()+"%"));
                Predicate[] arrayOr = new Predicate[listOr.size()];
                Predicate Pre_Or = criteriaBuilder.or(listOr.toArray(arrayOr));
                return criteriaQuery.where(Pre_And,Pre_Or).getRestriction();
            }
            return criteriaQuery.where(listAnd.toArray(new Predicate[0])).getRestriction();
        },pageable);
    }

    @Transactional
    public void insert(ReviewPlanAddReqVO vo) {
        ReviewPlan reviewPlan = new ReviewPlan();
        Information information = informationRepository.getOne(vo.getIid());
        BeanMapper.mapExcludeNull(vo,reviewPlan);
        reviewPlan.setInformation(information);
        reviewPlanRepository.save(reviewPlan);
    }

    /**
     * 医生给病人添加复查计划
     * @param vos
     */
    @Transactional
    @AddReviewAnnotation
    public void insertAll(List<ReviewPlanAddReqVO> vos) {
        vos.forEach(vo ->{
            ReviewPlan reviewPlan = new ReviewPlan();
            Information information = informationRepository.getOne(vo.getIid());
            BeanMapper.mapExcludeNull(vo,reviewPlan);
            reviewPlan.setInformation(information);
            reviewPlanRepository.save(reviewPlan);
        });
    }

    @Transactional
    public void update(ReviewPlanUpdateReqVO vo) {
        ReviewPlan reviewPlan = reviewPlanRepository.getOne(vo.getId());
        BeanUtils.copyPropertiesIgnoreNull(vo,reviewPlan);
        reviewPlanRepository.save(reviewPlan);
    }

    @Transactional
    public void delete(List<Integer> ids) {
        reviewPlanRepository.deleteBatch(ids);
    }

    /**
     * 根据医生id 获取未回复的检测信息
     * @param did
     * @return
     */
    public List<ReviewPlan> findByDid(Integer did) {
        return reviewPlanRepository.getByInformation_Doctor_IdAndExaminationNotNullAndReplyIsNullOrderByCreateTimeDesc(did);
    }


    /**
     * 获取n天后需要复查的计划
     * @param day
     * @return
     */
    public List<ReviewPlan> findByNearDay(Integer day){
        LocalDate date = LocalDate.now().plusDays(day);
        return reviewPlanRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> listAnd = new ArrayList<Predicate>();
            listAnd.add(criteriaBuilder.isNull(root.get("examination")));
            listAnd.add(criteriaBuilder.equal(root.get("reviewTime"),date));
            return criteriaQuery.where(listAnd.toArray(new Predicate[0])).getRestriction();
        });
    }

}
