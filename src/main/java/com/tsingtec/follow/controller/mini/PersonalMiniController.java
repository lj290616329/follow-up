package com.tsingtec.follow.controller.mini;

import com.tsingtec.follow.config.jwt.JwtUtil;
import com.tsingtec.follow.entity.mini.Doctor;
import com.tsingtec.follow.entity.mini.MaUser;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.DoctorService;
import com.tsingtec.follow.service.mini.MaUserService;
import com.tsingtec.follow.utils.HttpContextUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lj
 * @Date 2021/5/31 12:51
 * @Version 1.0
 */
@Slf4j
@RestController
@Api(tags = "小程序接口")
@RequestMapping("/api/index")
public class PersonalMiniController {

    @Autowired
    private MaUserService maUserService;
    @Autowired
    private DoctorService doctorService;

    @Resource(name="JwtUtil")
    private JwtUtil jwtUtil;

    public DataResult index(){
        String token = HttpContextUtils.getToken();
        MaUser maUser = maUserService.get(jwtUtil.getClaim(token,"id"));
        Doctor doctor = doctorService.get(maUser.getDid());



        return DataResult.success();
    }

}
