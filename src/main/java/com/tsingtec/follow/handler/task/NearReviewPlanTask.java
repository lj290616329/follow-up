package com.tsingtec.follow.handler.task;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.google.common.collect.Lists;
import com.tsingtec.follow.entity.mini.*;
import com.tsingtec.follow.service.mini.ReviewPlanService;
import com.tsingtec.follow.service.mini.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/11/4 17:32
 * @Version 1.0
 */
@Slf4j
@Component
@EnableScheduling
public class NearReviewPlanTask {

    @Autowired
    private ReviewPlanService reviewPlanService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private WxMaService maService;

    @Scheduled(cron = "0 0 9 * * ?")
    public void sendUniformMsg(){
        log.info("come to sendUniformMsg");
        List<ReviewPlan> reviewPlans = reviewPlanService.findByNearDay(5);
        reviewPlans.forEach(reviewPlan -> {
            final String tmplId = "Uw13UmFttdBbvz3YDHNQ1VghuFaoUUFOa2oEH3c-nqE";

            Information information = reviewPlan.getInformation();

            MaUser maUser = information.getMaUser();

            if(null!=maUser){
                Subscription subscription = subscriptionService.findByUidAndTmplId(maUser.getId(),tmplId);

                if(subscription!=null && subscription.getAccept()){

                    WxMaSubscribeMessage wxMaSubscribeMessage = new WxMaSubscribeMessage();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    List<WxMaSubscribeMessage.MsgData> data = Lists.newArrayList();
                    data.add(new WxMaSubscribeMessage.MsgData("thing2",information.getName()+":您近期有复查计划需要提交"));
                    data.add(new WxMaSubscribeMessage.MsgData("thing4", reviewPlan.getReviewTime().format(formatter)));

                    wxMaSubscribeMessage.setMiniprogramState("developer");

                    wxMaSubscribeMessage.setPage("/pages/personal/index");
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
            }else {
                log.info("用户未进行认证!无法发送通知");
            }

        });
    }
}
