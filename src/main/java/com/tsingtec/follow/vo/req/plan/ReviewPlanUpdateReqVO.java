package com.tsingtec.follow.vo.req.plan;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/6/2 11:30
 * @Version 1.0
 */
@Data
public class ReviewPlanUpdateReqVO {

    @NotNull(message = "id不能为空")
    private Integer id;

    private List<String> examine;

    private LocalDate reviewTime;//复查时间
}
