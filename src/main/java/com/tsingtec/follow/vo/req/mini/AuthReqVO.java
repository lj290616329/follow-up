package com.tsingtec.follow.vo.req.mini;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2021/6/30 15:17
 * @Version 1.0
 */
@Data
public class AuthReqVO {

    @NotNull(message="姓名不能为空")
    private String name;

    @NotNull(message="手机号码不能为空")
    private String phone;
}
