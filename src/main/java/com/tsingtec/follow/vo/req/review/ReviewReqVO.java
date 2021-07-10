package com.tsingtec.follow.vo.req.review;

import com.tsingtec.follow.entity.Examine;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2021/7/8 16:31
 * @Version 1.0
 */
@Data
public class ReviewReqVO {

    @NotNull(message = "id不能为空")
    private Integer id;

    private Examine examine;

    private String other;

    private String reply;//回复内容
}
