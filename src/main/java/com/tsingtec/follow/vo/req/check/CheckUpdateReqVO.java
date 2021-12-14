package com.tsingtec.follow.vo.req.check;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2021/12/9 15:17
 * @Version 1.0
 */
@Data
public class CheckUpdateReqVO {
    @NotBlank(message = "id不能为空")
    private Integer id;

    @NotNull(message = "名称不能为空")
    private String name;//检测名称

    @NotNull(message = "所属类别")
    private String category;//所属类别
}
