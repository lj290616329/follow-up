package com.tsingtec.follow.vo.req.mini;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2021/5/31 14:34
 * @Version 1.0
 */
@Data
public class PhoneReqVO {
    @NotNull(message="手机号码不能为空")
    private String phone;
}
