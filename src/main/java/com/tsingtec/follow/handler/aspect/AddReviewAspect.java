package com.tsingtec.follow.handler.aspect;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tsingtec.follow.entity.mini.Doctor;
import com.tsingtec.follow.entity.mini.Information;
import com.tsingtec.follow.entity.mini.MaUser;
import com.tsingtec.follow.entity.mini.Subscription;
import com.tsingtec.follow.service.mini.InformationService;
import com.tsingtec.follow.service.mini.SubscriptionService;
import com.tsingtec.follow.vo.req.plan.ReviewPlanAddReqVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Aspect
@Component
public class AddReviewAspect {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private WxMaService maService;

    @Autowired
    private InformationService informationService;

    /**
     * 此处的切点是注解的方式
     * 只要出现 @LogAnnotation注解都会进入
     */
    @Pointcut("@annotation(com.tsingtec.follow.handler.annotation.AddReviewAnnotation)")
    public void addReviewPointCut(){

    }

    /**
     * 环绕增强,相当于MethodInterceptor
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("addReviewPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //执行方法
        Object result = point.proceed();
        log.error("应该要来吖");
        //保存日志
        try {
            sendUniformMsg(point);
        } catch (Exception e) {
            log.error("e={}",e);
        }

        return result;
    }

    private void sendUniformMsg(ProceedingJoinPoint joinPoint) {
        if(null==joinPoint.getArgs()) return;

        System.out.println(joinPoint.getArgs().toString());
        Object arg = joinPoint.getArgs()[0];

        String jsonString = JSON.toJSONString( arg );
        System.out.println(jsonString);
        List<ReviewPlanAddReqVO> list = JSONObject.parseArray( jsonString, ReviewPlanAddReqVO.class );

        final String tmplId = "Uw13UmFttdBbvz3YDHNQ1VghuFaoUUFOa2oEH3c-nqE";

        Information information = informationService.findById(list.get(0).getIid());

        Doctor doctor = information.getDoctor();

        MaUser maUser = information.getMaUser();

        Subscription subscription = subscriptionService.findByUidAndTmplId(maUser.getId(),tmplId);

        if(subscription!=null && subscription.getAccept()){

            WxMaSubscribeMessage wxMaSubscribeMessage = new WxMaSubscribeMessage();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            List<WxMaSubscribeMessage.MsgData> data = Lists.newArrayList();
            data.add(new WxMaSubscribeMessage.MsgData("thing2","您的医生["+doctor.getName()+"]刚才给您添加了复查计划"));
            data.add(new WxMaSubscribeMessage.MsgData("thing4", LocalDateTime.now().format(formatter)));

            wxMaSubscribeMessage.setMiniprogramState("formal");

            wxMaSubscribeMessage.setPage("/pages/personal/review/plan");
            wxMaSubscribeMessage.setTemplateId(tmplId);
            wxMaSubscribeMessage.setData(data);
            wxMaSubscribeMessage.setToUser(maUser.getOpenId());

            try {
                maService.getSubscribeService().sendSubscribeMsg(wxMaSubscribeMessage);
            } catch (WxErrorException e) {
                e.printStackTrace();
                log.error("发送通知失败"+e.getMessage());
            }
        }else{
            log.info("用户未同意接收该信息");
        }

    }
}
