package com.tsingtec.follow.controller.mini;

import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.ReviewPlanService;
import com.tsingtec.follow.service.mini.ReviewService;
import com.tsingtec.follow.vo.req.review.ReviewReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lj
 * @Date 2021/7/8 16:28
 * @Version 1.0
 */
@Slf4j
@RestController
@Api(tags = "小程序接口")
@RequestMapping("/api")
public class ReviewMiniController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewPlanService reviewPlanService;

    @GetMapping("review")
    public DataResult detail(Integer id){
        return DataResult.success(reviewService.findById(id));
    }

    @PostMapping("review")
    public DataResult add(@RequestBody ReviewReqVO vo){
        reviewService.add(vo);
        return DataResult.success();
    }

    /**
     * 回复
     * @param vo
     * @return
     */
    @PutMapping("review")
    public DataResult update(@RequestBody ReviewReqVO vo){
        reviewService.update(vo);
        return DataResult.success();
    }
}
