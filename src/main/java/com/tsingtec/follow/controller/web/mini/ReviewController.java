package com.tsingtec.follow.controller.web.mini;

import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.ReviewService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lj
 * @Date 2021/7/6 16:07
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@Api(tags = "用户模块-照护计划")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("review")
    public DataResult detail(Integer id){
        return DataResult.success(reviewService.findById(id));
    }
}
