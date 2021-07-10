package com.tsingtec.follow.controller.mini;

import com.tsingtec.follow.config.jwt.JwtUtil;
import com.tsingtec.follow.entity.mini.Doctor;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.DoctorService;
import com.tsingtec.follow.service.mini.ReviewPlanService;
import com.tsingtec.follow.utils.HttpContextUtils;
import com.tsingtec.follow.vo.req.information.InformationPageReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @Resource(name="JwtUtil")
    private JwtUtil jwtUtil;

    @GetMapping("reviewPlan/list")
    public DataResult list(InformationPageReqVO vo){
        String token = HttpContextUtils.getToken();
        Doctor doctor = doctorService.findByUid(jwtUtil.getClaim(token,"id"));
        vo.setDid(doctor.getId());
        return DataResult.success(reviewPlanService.findAll(vo));
    }

    @GetMapping("reviewPlan")
    public DataResult detail(Integer id){
        return DataResult.success(reviewPlanService.getById(id));
    }
}
