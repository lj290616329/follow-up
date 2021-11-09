package com.tsingtec.follow.vo.req.subscription;

import lombok.Data;

/**
 * @Author lj
 * @Date 2021/11/3 11:54
 * @Version 1.0
 */
@Data
public class SubscriptionReqVO {
    private String tmplId;//模板id
    private Boolean accept = true;//是否订阅
}
