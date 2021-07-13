package com.tsingtec.follow.controller.mini;

import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.ArticleService;
import com.tsingtec.follow.vo.req.news.ArticlePageReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lj
 * @Date 2021/7/9 15:59
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "管理模块-图文管理")
public class ArticleMiniController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("article")
    public DataResult page(ArticlePageReqVO vo){
        return DataResult.success(articleService.findAll(vo));
    }

    @GetMapping("article/{id}")
    public DataResult detail(@PathVariable("id")Integer id){
        return DataResult.success(articleService.get(id));
    }

}
