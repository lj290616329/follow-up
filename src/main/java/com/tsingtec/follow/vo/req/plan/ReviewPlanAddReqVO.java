package com.tsingtec.follow.vo.req.plan;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/6/2 10:31
 * @Version 1.0
 */
@Data
public class ReviewPlanAddReqVO {

    @NotNull(message="iid不能为空")
    private Integer iid;//姓名

    private List<String> examine;

    private LocalDate reviewTime;//复查时间

    private String remark;
}
