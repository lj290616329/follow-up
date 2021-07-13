package com.tsingtec.follow.controller.web.mini;

import com.tsingtec.follow.entity.sys.Admin;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.ArticleService;
import com.tsingtec.follow.utils.HttpContextUtils;
import com.tsingtec.follow.vo.req.news.ArticleAddReqVO;
import com.tsingtec.follow.vo.req.news.ArticlePageReqVO;
import com.tsingtec.follow.vo.req.news.ArticleUpdateReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lj
 * @Date 2021/7/9 15:59
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@Api(tags = "管理模块-图文管理")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("article")
    public DataResult page(ArticlePageReqVO vo){
        Admin admin = HttpContextUtils.getAdmin();
        vo.setUnionId(admin.getUnionId());
        return DataResult.success(articleService.findAll(vo));
    }

    @GetMapping("article/{id}")
    public DataResult detail(@PathVariable("id")Integer id){
        return DataResult.success(articleService.get(id));
    }

    @PostMapping("article")
    public DataResult add(@RequestBody ArticleAddReqVO vo){
        Admin admin = HttpContextUtils.getAdmin();
        vo.setUnionId(admin.getUnionId());
        articleService.insert(vo);
        return DataResult.success();
    }

    @PutMapping("article")
    public DataResult update(@RequestBody ArticleUpdateReqVO vo){
        articleService.update(vo);
        return DataResult.success();
    }

    @DeleteMapping("article")
    public DataResult del(@RequestBody List<Integer> ids){
        articleService.delete(ids);
        return DataResult.success();
    }
    
}
