package com.tsingtec.follow.vo.req.doctor;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2021/6/2 15:35
 * @Version 1.0
 */
@Data
public class DoctorInfoVO {

    @NotNull(message="姓名不能为空")
    private String name; //姓名

    private String pic;//头像

    @NotNull(message="手机号不能为空")
    private String phone; //电话

    private String goodAt; //擅长

    private String des; //简介
}
