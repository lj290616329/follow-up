package com.tsingtec.follow.vo.req.news;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lj
 * @Date 2021/7/9 15:06
 * @Version 1.0
 */
@Data
public class ArticlePageReqVO {
    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;
}
