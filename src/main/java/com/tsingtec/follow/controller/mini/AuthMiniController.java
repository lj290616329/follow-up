package com.tsingtec.follow.controller.mini;

import com.tsingtec.follow.config.jwt.JwtUtil;
import com.tsingtec.follow.entity.mini.Doctor;
import com.tsingtec.follow.entity.mini.Information;
import com.tsingtec.follow.entity.mini.MaUser;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.DoctorService;
import com.tsingtec.follow.service.mini.InformationService;
import com.tsingtec.follow.service.mini.MaUserService;
import com.tsingtec.follow.utils.HttpContextUtils;
import com.tsingtec.follow.vo.req.mini.PhoneReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author lj
 * @Date 2021/5/30 13:09
 * @Version 1.0
 */
@Api("用户认证模块")
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthMiniController {

    @Autowired
    private MaUserService maUserService;

    @Autowired
    private InformationService informationService;

    @Autowired
    private DoctorService doctorService;

    @Resource(name="JwtUtil")
    private JwtUtil jwtUtil;

    @PostMapping("information")
    public DataResult information(@RequestBody @Valid Information information){
        String token = HttpContextUtils.getToken();
        MaUser maUser = maUserService.get(jwtUtil.getClaim(token,"id"));
        information.setMaUser(maUser);
        informationService.save(information);
        return DataResult.success();
    }


    @PostMapping("doctor")
    public DataResult doctor(@RequestBody @Valid PhoneReqVO vo){
        String token = HttpContextUtils.getToken();
        MaUser maUser = maUserService.get(jwtUtil.getClaim(token,"id"));
        Doctor doctor = doctorService.findByPhone(vo.getPhone());
        if(null==doctor){
            return DataResult.fail("医生信息不存在,请联系管理员");
        }
        doctor.setMaUser(maUser);
        doctorService.save(doctor);

        Information information = new Information();
        information.setMaUser(maUser);
        information.setPhone(doctor.getPhone());
        information.setName(doctor.getName());
        information.setRecordNo(RandomStringUtils.random(10, "0123456789"));
        informationService.save(information);

        maUser.setDid(doctor.getId());
        maUserService.save(maUser);
        return DataResult.success();
    }
}
