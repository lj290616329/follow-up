package com.tsingtec.follow.controller.mini;

import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.handler.annotation.PassToken;
import com.tsingtec.follow.service.mini.DiseaseService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@Api(tags = "小程序-病种模块")
public class DiseaseMiniController {

    @Autowired
    private DiseaseService diseaseService;

    @PassToken
    @GetMapping("disease/tree")
    public DataResult tree(){
        return DataResult.success(diseaseService.getTreeDisease());
    }

    @PassToken
    @GetMapping("disease/all")
    public DataResult all(){
        return DataResult.success(diseaseService.findAll());
    }
}
