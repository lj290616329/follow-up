package com.tsingtec.follow.controller.web.mini;

import com.tsingtec.follow.entity.mini.Check;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.CheckService;
import com.tsingtec.follow.utils.BeanMapper;
import com.tsingtec.follow.vo.req.check.CheckAddReqVO;
import com.tsingtec.follow.vo.req.check.CheckPageReqVO;
import com.tsingtec.follow.vo.req.check.CheckUpdateReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@Api(tags = "管理模块-检查项目管理")
public class CheckController {

    @Autowired
    private CheckService checkService;

    @GetMapping("check")
    public DataResult page(CheckPageReqVO vo){
        Check check = BeanMapper.map(vo,Check.class);
        Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(), Sort.Direction.DESC,"id");
        return DataResult.success(checkService.page(check,pageable));
    }

    @GetMapping("check/{id}")
    public DataResult detail(@PathVariable("id")Integer id){
        return DataResult.success(checkService.get(id));
    }

    @PostMapping("check")
    public DataResult add(@RequestBody CheckAddReqVO vo){
        log.info(vo.toString());
        Check check = BeanMapper.map(vo,Check.class);


        checkService.save(check);
        return DataResult.success();
    }

    @PutMapping("check")
    public DataResult update(@RequestBody CheckUpdateReqVO vo){
        Check check = checkService.get(vo.getId());
        BeanMapper.mapExcludeNull(vo,check);
        checkService.save(check);
        return DataResult.success();
    }

    @DeleteMapping("check")
    public DataResult del(@RequestBody List<Integer> ids){
        checkService.delete(ids);
        return DataResult.success();
    }
    
}
