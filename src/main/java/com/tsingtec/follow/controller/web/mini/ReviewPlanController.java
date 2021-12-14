package com.tsingtec.follow.controller.web.mini;

import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.ReviewPlanService;
import com.tsingtec.follow.vo.req.plan.ReviewPlanAddReqVO;
import com.tsingtec.follow.vo.req.plan.ReviewPlanUpdateReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lj
 * @Date 2021/7/6 16:07
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@Api(tags = "用户模块-照护计划")
public class ReviewPlanController {

    @Autowired
    private ReviewPlanService reviewPlanService;

    /**
     * 根据用户id获取用户复查计划列表
     * @param iid
     * @return
     */
    @GetMapping("reviewPlan/{iid}")
    public DataResult list(@PathVariable("iid")Integer iid){
        return DataResult.success(reviewPlanService.pageByIid(iid));
    }


    @GetMapping("reviewPlan")
    public DataResult detail(Integer id){
        return DataResult.success(reviewPlanService.findCheckedById(id));
    }

    @PostMapping("reviewPlan")
    public DataResult add(@RequestBody List<ReviewPlanAddReqVO> vo){
        reviewPlanService.insertAll(vo);
        return DataResult.success();
    }

    @PutMapping("reviewPlan")
    public DataResult update(@RequestBody ReviewPlanUpdateReqVO vo){
        reviewPlanService.update(vo);
        return DataResult.success();
    }

    @DeleteMapping("reviewPlan")
    public DataResult del(@RequestBody List<Integer> ids){
        reviewPlanService.delete(ids);
        return DataResult.success();
    }
    
}
