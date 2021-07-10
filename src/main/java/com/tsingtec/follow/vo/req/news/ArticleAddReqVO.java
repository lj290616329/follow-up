package com.tsingtec.follow.vo.req.news;

import lombok.Data;

import java.util.List;

/**
 * @Author lj
 * @Date 2021/7/9 15:36
 * @Version 1.0
 */
@Data
public class ArticleAddReqVO {
    private String title;

    private String pic;

    private String des;

    private String content;

    private List<String> tags;
}
