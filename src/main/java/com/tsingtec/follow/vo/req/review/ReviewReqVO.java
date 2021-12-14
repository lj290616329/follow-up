package com.tsingtec.follow.vo.req.review;

import com.tsingtec.follow.entity.Examination;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/7/8 16:31
 * @Version 1.0
 */
@Data
public class ReviewReqVO {

    @NotNull(message = "id不能为空")
    private Integer id;

    private List<Examination> examination;

    private String other;

    private String reply;//回复内容
}
