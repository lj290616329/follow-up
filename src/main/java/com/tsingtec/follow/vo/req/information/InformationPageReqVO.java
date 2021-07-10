package com.tsingtec.follow.vo.req.information;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lj
 * @Date 2021/7/5 10:32
 * @Version 1.0
 */
@Data
public class InformationPageReqVO {

    @ApiModelProperty(value = "姓名或者电话")
    private String title;

    private Integer did;

    @ApiModelProperty(value = "病历号")
    private String recordNo;//

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

}
