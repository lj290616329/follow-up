package com.tsingtec.follow.controller.mini;

import com.tsingtec.follow.config.jwt.JwtUtil;
import com.tsingtec.follow.entity.mini.Doctor;
import com.tsingtec.follow.entity.mini.Information;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.DoctorService;
import com.tsingtec.follow.service.mini.InformationService;
import com.tsingtec.follow.service.mini.ReviewPlanService;
import com.tsingtec.follow.utils.BeanUtils;
import com.tsingtec.follow.utils.HttpContextUtils;
import com.tsingtec.follow.vo.req.information.InfoEditReqVO;
import com.tsingtec.follow.vo.req.information.InformationPageReqVO;
import com.tsingtec.follow.vo.resp.doctor.InfoDetailRespVO;
import com.tsingtec.follow.vo.resp.review.ReviewPlanRespVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/5/30 13:09
 * @Version 1.0
 */
@Slf4j
@RestController
@Api(tags="用户信息模块")
@RequestMapping("/api")
public class InformationMiniController {

    @Autowired
    private InformationService informationService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ReviewPlanService reviewPlanService;

    @Resource(name="JwtUtil")
    private JwtUtil jwtUtil;


    /**
     * 个人获取用户信息
     * @return
     */
    @GetMapping("information")
    public DataResult information(){
        String token = HttpContextUtils.getToken();
        Information information = informationService.findByUid(jwtUtil.getClaim(token,"id"));
        return DataResult.success(information);
    }


    @GetMapping("information/list")
    public DataResult list(InformationPageReqVO vo){
        String token = HttpContextUtils.getToken();
        Doctor doctor = doctorService.findByUid(jwtUtil.getClaim(token,"id"));
        vo.setDid(doctor.getId());
        return DataResult.success(informationService.findAll(vo));
    }


    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("information/{id}")
    public DataResult information(@PathVariable(name = "id")Integer id){
        Information information = informationService.findById(id);
        List<ReviewPlanRespVO> reviewPlans = reviewPlanService.findByIid(id);
        return DataResult.success(new InfoDetailRespVO(information,reviewPlans));
    }

    /**
     * 编辑病人用户信息
     * @param vo
     * @return
     */
    @PutMapping("information")
    private DataResult edit(@RequestBody @Valid InfoEditReqVO vo){
        Information information = informationService.findById(vo.getId());
        BeanUtils.copyPropertiesIgnoreNull(vo,information);
        informationService.save(information);
        return DataResult.success();
    }
}
