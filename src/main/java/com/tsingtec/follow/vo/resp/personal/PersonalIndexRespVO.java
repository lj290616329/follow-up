package com.tsingtec.follow.vo.resp.personal;

import com.tsingtec.follow.entity.mini.ReviewPlan;
import lombok.Data;

/**
 * @Author lj
 * @Date 2021/7/8 15:35
 * @Version 1.0
 */
@Data
public class PersonalIndexRespVO {
    private ReviewPlan near;
    private ReviewPlan reply;

    public PersonalIndexRespVO(ReviewPlan near,ReviewPlan reply){
        this.near = near;
        this.reply = reply;
    }
}
