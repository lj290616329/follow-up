package com.tsingtec.follow.vo.resp.doctor;

import com.tsingtec.follow.entity.mini.Information;
import com.tsingtec.follow.vo.resp.review.ReviewPlanRespVO;
import lombok.Data;

import java.util.List;

/**
 * @Author lj
 * @Date 2021/7/8 10:46
 * @Version 1.0
 */
@Data
public class InfoDetailRespVO {
    private Information information;
    private List<ReviewPlanRespVO> reviewPlans;

    public InfoDetailRespVO(Information information,List<ReviewPlanRespVO> reviewPlans){
        this.information = information;
        this.reviewPlans = reviewPlans;
    }
}
