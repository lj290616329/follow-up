package com.tsingtec.follow.controller.mini;

import com.tsingtec.follow.config.jwt.JwtUtil;
import com.tsingtec.follow.entity.mini.Doctor;
import com.tsingtec.follow.entity.mini.Information;
import com.tsingtec.follow.entity.mini.ReviewPlan;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.handler.annotation.ReplyAnnotation;
import com.tsingtec.follow.handler.annotation.ReviewAnnotation;
import com.tsingtec.follow.service.mini.DoctorService;
import com.tsingtec.follow.service.mini.InformationService;
import com.tsingtec.follow.service.mini.ReviewPlanService;
import com.tsingtec.follow.utils.BeanMapper;
import com.tsingtec.follow.utils.BeanUtils;
import com.tsingtec.follow.utils.HttpContextUtils;
import com.tsingtec.follow.vo.req.information.InformationPageReqVO;
import com.tsingtec.follow.vo.req.plan.ReviewPlanExaminationReqVO;
import com.tsingtec.follow.vo.req.plan.ReviewPlanReplyReqVO;
import com.tsingtec.follow.vo.resp.review.ReviewPlanDetailRespVO;
import com.tsingtec.follow.vo.resp.review.ReviewPlanRespVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/7/8 14:35
 * @Version 1.0
 */
@Slf4j
@RestController
@Api("用户信息模块")
@RequestMapping("/api")
public class ReviewPlanMiniController {

    @Autowired
    private ReviewPlanService reviewPlanService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private InformationService informationService;


    @Resource(name = "JwtUtil")
    private JwtUtil jwtUtil;


    @GetMapping("reviewPlan/list")
    public DataResult planlist() {
        String token = HttpContextUtils.getToken();
        Information information = informationService.findByUid(jwtUtil.getClaim(token, "id"));
        List<ReviewPlanRespVO> reviewPlans = reviewPlanService.findByIid(information.getId());
        return DataResult.success(reviewPlans);
    }

    @GetMapping("reviewPlan/page")
    public DataResult list(InformationPageReqVO vo) {
        String token = HttpContextUtils.getToken();
        Doctor doctor = doctorService.findByUid(jwtUtil.getClaim(token, "id"));
        vo.setDid(doctor.getId());
        return DataResult.success(reviewPlanService.findAll(vo));
    }

    @GetMapping("reviewPlan")
    public DataResult detail(Integer id) {
        return DataResult.success(BeanMapper.map(reviewPlanService.getById(id), ReviewPlanDetailRespVO.class));
    }

    @ReviewAnnotation
    @PutMapping("reviewPlan")
    public DataResult update(@RequestBody ReviewPlanExaminationReqVO vo){
        log.info("{}",vo);
        reviewPlanService.save(vo);
        return DataResult.success();
    }


    @ReplyAnnotation
    @PutMapping("reviewPlan/reply")
    public DataResult reply(@RequestBody ReviewPlanReplyReqVO vo){
        log.info("{}",vo);
        ReviewPlan reviewPlan = reviewPlanService.getById(vo.getId());
        BeanMapper.mapExcludeNull(vo,reviewPlan);
        reviewPlanService.save(reviewPlan);
        return DataResult.success();
    }


    @GetMapping("reviewPlan/reply")
    public DataResult replys(@RequestBody ReviewPlanReplyReqVO vo){
        log.info("{}",vo);
        ReviewPlan reviewPlan = reviewPlanService.getById(vo.getId());
        BeanUtils.copyPropertiesIgnoreNull(vo,reviewPlan);
        reviewPlanService.save(reviewPlan);
        return DataResult.success();
    }

}
