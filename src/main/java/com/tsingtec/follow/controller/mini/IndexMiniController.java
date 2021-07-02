package com.tsingtec.follow.controller.mini;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.tsingtec.follow.config.jwt.JwtUtil;
import com.tsingtec.follow.config.mini.WxMaConfiguration;
import com.tsingtec.follow.entity.mini.Doctor;
import com.tsingtec.follow.entity.mini.Information;
import com.tsingtec.follow.entity.mini.MaUser;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.mini.DoctorService;
import com.tsingtec.follow.service.mini.InformationService;
import com.tsingtec.follow.service.mini.MaUserService;
import com.tsingtec.follow.utils.BeanMapper;
import com.tsingtec.follow.vo.req.mini.WxLoginReqVO;
import com.tsingtec.follow.vo.resp.mini.BaseUserRespVO;
import com.tsingtec.follow.vo.resp.mini.TokenRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Slf4j
@RestController
@Api(tags = "小程序接口")
@RequestMapping("/api/index")
public class IndexMiniController {

    @Autowired
    private MaUserService maUserService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private InformationService informationService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录接口
     */
    @GetMapping("/login")
    @ApiOperation(value = "用户登录接口")
    public DataResult login(String code) {
        BaseUserRespVO baseUserRespVO = new BaseUserRespVO();
        if (StringUtils.isBlank(code)) {
            return DataResult.fail("授权信息不全,请重新进行授权!");
        }
        final WxMaService wxService = WxMaConfiguration.getMaService();

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            log.info(session.toString());
            MaUser maUser = maUserService.findByOpenId(session.getOpenid());
            if(null != maUser){
                TokenRespVO token = jwtUtil.getToken(maUser);
                baseUserRespVO.setToken(token);
                Information information = informationService.findByUid(maUser.getId());
                Doctor doctor = doctorService.findByUid(maUser.getId());
                if(null==information && null==doctor){
                    return DataResult.fail("登录失败!");
                }
                if(doctorService.findByUid(maUser.getId())!=null){
                    baseUserRespVO.setIfDoctor(true);
                }
            }else{
                return DataResult.fail("登录失败!");
            }
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return DataResult.fail("登录失败!");
        }
        return DataResult.success(baseUserRespVO);
    }

    /**
     * 重新获取token
     * @param refreshToken
     * @return
     */
    @GetMapping("/refresh")
    public DataResult refresh(@RequestParam("refreshToken") String refreshToken){
        // 执行认证
        if (refreshToken == null) {
            return DataResult.fail("获取失败!");
        }
        // 获取 token 中的 user id
        if(!jwtUtil.verify(refreshToken)){
            return DataResult.fail("token 认证失败!");
        }
        Integer id = jwtUtil.getClaim(refreshToken,"id");
        MaUser maUser = maUserService.get(id);
        return DataResult.success(jwtUtil.getToken(maUser));
    }

    /**
     * 授权 获取身份信息
     * @param wxLoginVo
     * @return
     */
    @PostMapping("/auth")
    @ApiOperation(value = "用户授权接口")
    public DataResult sign(@RequestBody WxLoginReqVO wxLoginVo){
        BaseUserRespVO baseUserRespVO = new BaseUserRespVO();
        final WxMaService wxService = WxMaConfiguration.getMaService();

        log.info("登录信息为:{}",wxLoginVo);
        String code = wxLoginVo.getCode();
        if(StringUtils.isBlank(code)){
            return DataResult.fail("授权信息不全,请重新进行授权");
        }
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            if (!wxService.getUserService().checkUserInfo(session.getSessionKey(), wxLoginVo.getRawData(), wxLoginVo.getSignature())) {
                return DataResult.fail("user check failed");
            }
            // 解密用户信息
            WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(session.getSessionKey(), wxLoginVo.getEncryptedData(), wxLoginVo.getIv());
            MaUser maUser = new MaUser();

            BeanMapper.mapExcludeNull(userInfo,maUser);
            maUser.setDid(wxLoginVo.getDid());
            maUser.setUnionId(session.getUnionid());
            maUser.setOpenId(session.getOpenid());
            maUser = maUserService.save(maUser);

            TokenRespVO token = jwtUtil.getToken(maUser);
            baseUserRespVO.setToken(token);
        }catch (WxErrorException e) {
            return DataResult.fail("授权失败,请稍后再试!");
        }
        return DataResult.success(baseUserRespVO);
    }


    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @PostMapping("/phone")
    public DataResult phone(@RequestBody WxLoginReqVO wxLoginVo) {
        log.error(wxLoginVo.toString());
        final WxMaService wxService = WxMaConfiguration.getMaService();
        String code = wxLoginVo.getCode();
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            // 解密
            WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(session.getSessionKey(), wxLoginVo.getEncryptedData(), wxLoginVo.getIv());
            return DataResult.success(phoneNoInfo.getPurePhoneNumber());
        } catch (WxErrorException e) {
            e.printStackTrace();
            return DataResult.fail("手机号获取失败!");
        }
    }
}