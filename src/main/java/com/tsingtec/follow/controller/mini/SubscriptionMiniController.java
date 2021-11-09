package com.tsingtec.follow.controller.mini;

import com.tsingtec.follow.config.jwt.JwtUtil;
import com.tsingtec.follow.entity.mini.Subscription;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.SubscriptionService;
import com.tsingtec.follow.utils.BeanMapper;
import com.tsingtec.follow.utils.HttpContextUtils;
import com.tsingtec.follow.vo.req.subscription.SubscriptionReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lj
 * @Date 2021/7/9 15:59
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "管理模块-图文管理")
public class SubscriptionMiniController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("subscription")
    public DataResult detail(@RequestBody SubscriptionReqVO vo){
        Integer uid = jwtUtil.getClaim(HttpContextUtils.getToken(),"id");
        Subscription subscription = subscriptionService.findByUidAndTmplId(uid,vo.getTmplId());
        if(subscription==null) subscription = new Subscription();
        BeanMapper.mapExcludeNull(vo,subscription);
        subscription.setUid(uid);
        subscriptionService.save(subscription);
        return DataResult.success();
    }
}
