package com.tsingtec.follow.vo.req.doctor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lj
 * @Date 2021/6/22 10:40
 * @Version 1.0
 */
@Data
public class DoctorPageReqVO {

    @ApiModelProperty(value = "姓名或者电话")
    private String title;

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

}
