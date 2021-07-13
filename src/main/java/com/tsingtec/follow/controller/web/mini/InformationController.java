package com.tsingtec.follow.controller.web.mini;

import com.tsingtec.follow.entity.sys.Admin;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.InformationService;
import com.tsingtec.follow.utils.HttpContextUtils;
import com.tsingtec.follow.vo.req.information.InformationAddReqVO;
import com.tsingtec.follow.vo.req.information.InformationPageReqVO;
import com.tsingtec.follow.vo.req.information.InformationUpdateReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lj
 * @Date 2021/6/22 10:38
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@Api(tags = "用户模块-医生管理")
public class InformationController {

    @Autowired
    private InformationService informationService;

    @GetMapping("information")
    public DataResult page(InformationPageReqVO vo){
        Admin admin = HttpContextUtils.getAdmin();
        vo.setDid(admin.getUnionId());
        return DataResult.success(informationService.findAll(vo));
    }


    @GetMapping("information/{id}")
    public DataResult detail(@PathVariable("id")Integer id){
        return DataResult.success(informationService.findById(id));
    }

    @PostMapping("information")
    public DataResult add(@RequestBody InformationAddReqVO vo){
        informationService.insert(vo);
        return DataResult.success();
    }

    @PutMapping("information")
    public DataResult update(@RequestBody InformationUpdateReqVO vo){
        informationService.update(vo);
        return DataResult.success();
    }

    @DeleteMapping("information")
    public DataResult del(@RequestBody List<Integer> ids){
        informationService.delete(ids);
        return DataResult.success();
    }
}
