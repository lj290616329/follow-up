package com.tsingtec.follow.vo.req.check;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2021/12/9 15:15
 * @Version 1.0
 */
@Data
public class CheckAddReqVO {

    @NotNull(message = "名称不能为空")
    private String name;//检测名称

    private String enName;//检测英文名

    @NotNull(message = "所属类别")
    private String category;//所属类别
}
