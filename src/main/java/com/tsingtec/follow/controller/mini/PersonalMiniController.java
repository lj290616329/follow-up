package com.tsingtec.follow.controller.mini;

import com.tsingtec.follow.config.jwt.JwtUtil;
import com.tsingtec.follow.entity.mini.Information;
import com.tsingtec.follow.entity.mini.ReviewPlan;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.InformationService;
import com.tsingtec.follow.service.mini.ReviewPlanService;
import com.tsingtec.follow.utils.HttpContextUtils;
import com.tsingtec.follow.vo.resp.personal.PersonalIndexRespVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lj
 * @Date 2021/5/31 12:51
 * @Version 1.0
 */
@Slf4j
@RestController
@Api(tags = "用户首页接口")
@RequestMapping("/api/personal")
public class PersonalMiniController {

    @Autowired
    private InformationService informationService;

    @Autowired
    private ReviewPlanService reviewPlanService;

    @Resource(name="JwtUtil")
    private JwtUtil jwtUtil;


    @GetMapping("index")
    public DataResult index(){
        String token = HttpContextUtils.getToken();
        Information information = informationService.findByUid(jwtUtil.getClaim(token,"id"));
        ReviewPlan reviewPlan = reviewPlanService.nearByIid(information.getId());
        ReviewPlan reply = reviewPlanService.nearReplyByIid(information.getId());
        return DataResult.success(new PersonalIndexRespVO(reviewPlan,reply));
    }
}
