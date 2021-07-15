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
import com.tsingtec.follow.vo.req.mini.AuthReqVO;
import com.tsingtec.follow.vo.req.mini.SceneReqVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
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

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "用户认证模块")
public class AuthMiniController {

    @Autowired
    private MaUserService maUserService;

    @Autowired
    private InformationService informationService;

    @Autowired
    private DoctorService doctorService;

    @Resource(name="JwtUtil")
    private JwtUtil jwtUtil;

    @Resource(name = "ehCacheManager")
    private EhCacheManager mycacheManager;

    @PostMapping("auth")
    public DataResult auth(@RequestBody @Valid AuthReqVO vo){
        String token = HttpContextUtils.getToken();
        MaUser maUser = maUserService.get(jwtUtil.getClaim(token,"id"));
        Information information = informationService.findByPhone(vo.getPhone());
        if(information != null){
            information.setMaUser(maUser);
            informationService.save(information);
            return DataResult.success(false);
        }

        Doctor doctor = doctorService.findByPhone(vo.getPhone());

        if(doctor != null){
            doctor.setMaUser(maUser);
            doctorService.save(doctor);
            return DataResult.success(true);
        }
        return DataResult.fail("注册失败,请联系管理");
    }

    @PostMapping("agree")
    public DataResult agree(@RequestBody SceneReqVO vo){
        String token = HttpContextUtils.getToken();
        MaUser maUser = maUserService.get(jwtUtil.getClaim(token,"id"));
        if(null==maUser){
            return DataResult.fail("请先注册认证后再进行扫描登陆");
        }
        Doctor doctor = doctorService.findByUid(maUser.getId());
        if(null == doctor){
            return DataResult.fail("您不是医生用户,无法进行此操作");
        }
        Cache<String, String> cache1 = mycacheManager.getCache("login");
        if(cache1.get(vo.getScene())==null){
            return DataResult.fail("操作过期,请刷新二维码再试");
        }
        Cache<String, String> cache = mycacheManager.getCache("agree");
        cache.put(vo.getScene(),doctor.getPhone());
        return DataResult.success();
    }
}
