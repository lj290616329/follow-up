package com.tsingtec.follow.handler.aspect;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.google.common.collect.Lists;
import com.tsingtec.follow.config.jwt.JwtUtil;
import com.tsingtec.follow.entity.mini.Doctor;
import com.tsingtec.follow.entity.mini.Information;
import com.tsingtec.follow.entity.mini.MaUser;
import com.tsingtec.follow.entity.mini.Subscription;
import com.tsingtec.follow.service.mini.InformationService;
import com.tsingtec.follow.service.mini.SubscriptionService;
import com.tsingtec.follow.utils.BeanMapper;
import com.tsingtec.follow.utils.HttpContextUtils;
import com.tsingtec.follow.vo.req.plan.ReviewPlanExaminationReqVO;
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
public class ReviewAspect {

    @Autowired
    private InformationService informationService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private WxMaService maService;

    /**
     * 此处的切点是注解的方式
     * 只要出现 @LogAnnotation注解都会进入
     */
    @Pointcut("@annotation(com.tsingtec.follow.handler.annotation.ReviewAnnotation)")
    public void reviewPointCut(){

    }

    /**
     * 环绕增强,相当于MethodInterceptor
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("reviewPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //执行方法
        Object result = point.proceed();
        try {
            sendUniformMsg(point);
        } catch (Exception e) {
            log.error("e={}",e);
        }

        return result;
    }

    private void sendUniformMsg(ProceedingJoinPoint joinPoint) {
        if(null==joinPoint.getArgs()) return;
        log.info("进入向医生发送通知");
        final String tmplId = "iNbbQStWha87QupCzPHTmNmXvedPSmbKuAz7M4rJwt4";

        Integer uid = jwtUtil.getClaim(HttpContextUtils.getToken(),"id");

        Information information = informationService.findByUid(uid);

        Doctor doctor = information.getDoctor();
        /**
         * 医生被删除
         */
        if(null == doctor) return;

        MaUser maUser = doctor.getMaUser();

        if(null == maUser) return;

        Subscription subscription = subscriptionService.findByUidAndTmplId(maUser.getId(),tmplId);

        Object arg = joinPoint.getArgs()[0];

        ReviewPlanExaminationReqVO vo = BeanMapper.map(arg, ReviewPlanExaminationReqVO.class);

        if(subscription!=null && subscription.getAccept()){

            WxMaSubscribeMessage wxMaSubscribeMessage = new WxMaSubscribeMessage();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            List<WxMaSubscribeMessage.MsgData> data = Lists.newArrayList();
            data.add(new WxMaSubscribeMessage.MsgData("thing1","您的病人["+information.getName()+"]刚才提交了一份复查信息"));
            data.add(new WxMaSubscribeMessage.MsgData("thing3",LocalDateTime.now().format(formatter)));

            wxMaSubscribeMessage.setMiniprogramState("formal");
            wxMaSubscribeMessage.setPage("/pages/doctor/check/detail?id="+vo.getId());
            wxMaSubscribeMessage.setTemplateId(tmplId);
            wxMaSubscribeMessage.setData(data);
            wxMaSubscribeMessage.setToUser(maUser.getOpenId());

            try {
                maService.getSubscribeService().sendSubscribeMsg(wxMaSubscribeMessage);
            } catch (WxErrorException e) {
                e.printStackTrace();
                log.error("发送通知失败"+e.getMessage());
            }
        }

    }
}
