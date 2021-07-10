package com.tsingtec.follow.vo.req.news;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author lj
 * @Date 2021/7/9 15:38
 * @Version 1.0
 */
@Data
public class ArticleUpdateReqVO {

    @ApiModelProperty(value = "文章id",name="id")
    @NotNull(message = "文章id不能为空")
    private Integer id;

    @ApiModelProperty(value = "文章标题",name="title")
    @NotBlank(message = "文章标题不能为空")
    private String title; //标题

    @ApiModelProperty(value = "封面图",name="pic")
    @NotBlank(message = "封面图不能为空")
    private String pic;//封面图

    @ApiModelProperty(value = "简介",name="description")
    private String des;//简介

    @ApiModelProperty(value = "文章内容",name="content")
    @NotBlank(message = "文章内容不能为空")
    private String content;//内容

    @ApiModelProperty(value = "文章类型",name="tag")
    @NotNull(message = "请选择文章类型")
    private List<String> tags; //标签类型
}
