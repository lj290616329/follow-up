package com.tsingtec.follow.controller.mini;

import com.tsingtec.follow.config.jwt.JwtUtil;
import com.tsingtec.follow.entity.mini.Information;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.InformationService;
import com.tsingtec.follow.service.mini.MaUserService;
import com.tsingtec.follow.utils.BeanMapper;
import com.tsingtec.follow.utils.HttpContextUtils;
import com.tsingtec.follow.vo.req.doctor.InfoEditReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author lj
 * @Date 2021/5/30 13:09
 * @Version 1.0
 */
@Slf4j
@RestController
@Api("用户信息模块")
@RequestMapping("/api")
public class InformationMiniController {

    @Autowired
    private InformationService informationService;

    @Autowired
    private MaUserService maUserService;

    @Resource(name="JwtUtil")
    private JwtUtil jwtUtil;


    @GetMapping("information")
    public DataResult information(){
        String token = HttpContextUtils.getToken();
        Information information = informationService.findByUid(jwtUtil.getClaim(token,"id"));
        return DataResult.success(information);
    }

    @GetMapping("information/{id}")
    public DataResult information(@PathVariable(name = "id")Integer id){
        return DataResult.success(informationService.findById(id));
    }

    /**
     * 编辑病人用户信息
     * @param vo
     * @return
     */
    @PutMapping("information")
    private DataResult edit(@RequestBody @Valid InfoEditReqVO vo){
        Information information = informationService.findById(vo.getId());
        BeanMapper.mapExcludeNull(vo,information);
        informationService.save(information);
        return DataResult.success();
    }
}
