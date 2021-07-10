package com.tsingtec.follow.controller.web.mini;

import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.DoctorService;
import com.tsingtec.follow.vo.req.doctor.DoctorAddReqVO;
import com.tsingtec.follow.vo.req.doctor.DoctorPageReqVO;
import com.tsingtec.follow.vo.req.doctor.DoctorUpdateReqVO;
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
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("doctor")
    public DataResult page(DoctorPageReqVO vo){
        return DataResult.success(doctorService.findAll(vo));
    }

    @GetMapping("doctors")
    public DataResult all(){
        return DataResult.success(doctorService.getAll());
    }



    @GetMapping("doctor/{id}")
    public DataResult detail(@PathVariable("id")Integer id){
        return DataResult.success(doctorService.get(id));
    }

    @PostMapping("doctor")
    public DataResult add(@RequestBody DoctorAddReqVO vo){
        doctorService.insert(vo);
        return DataResult.success();
    }

    @PutMapping("doctor")
    public DataResult update(@RequestBody DoctorUpdateReqVO vo){
        doctorService.update(vo);
        return DataResult.success();
    }

    @DeleteMapping("doctor")
    public DataResult del(@RequestBody List<Integer> ids){
        doctorService.delete(ids);
        return DataResult.success();
    }
}
