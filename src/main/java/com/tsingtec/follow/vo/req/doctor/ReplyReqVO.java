package com.tsingtec.follow.vo.req.doctor;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2021/6/2 10:31
 * @Version 1.0
 */
@Data
public class ReplyReqVO {

    @NotNull(message="id不能为空")
    private Integer id; //姓名
    @NotNull(message="回复内容不能为空")
    private String reply;//头像

}
