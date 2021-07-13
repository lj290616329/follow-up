package com.tsingtec.follow.vo.req.mini;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2021/7/13 10:24
 * @Version 1.0
 */
@Data
public class SceneReqVO {
    @NotNull(message="姓名不能为空")
    private String scene;
}
