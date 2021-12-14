package com.tsingtec.follow.vo.req.plan;

import com.tsingtec.follow.entity.Examination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/6/2 10:31
 * @Version 1.0
 */
@Data
public class ReviewPlanExaminationReqVO {

    @NotNull(message="id不能为空")
    private Integer id;//姓名

    @ApiModelProperty(value = "复查结果")
    private List<Examination> examination;//病历

    private String other;

}
