package com.tsingtec.follow.vo.req.disease;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @Author lj
 * @Date 2021/12/10 16:31
 * @Version 1.0
 */
@Data
public class DiseasePageReqVO {
    private String title;

    @ApiModelProperty(value = "第几页")
    @Min(1)
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    @Min(1)
    private int pageSize=10;
}
