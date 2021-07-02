package com.tsingtec.follow.controller.web.mini;

import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.DoctorService;
import com.tsingtec.follow.vo.req.doctor.DoctorPageReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lj
 * @Date 2021/6/22 10:38
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@Api(tags = "用户模块-医生管理")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("doctor")
    public DataResult page(DoctorPageReqVO vo){
        return DataResult.success(doctorService.findAll(vo));
    }


    @GetMapping("doctor/{id}")
    public DataResult page(@PathVariable("id")Integer id){
        return DataResult.success(doctorService.get(id));
    }


}
