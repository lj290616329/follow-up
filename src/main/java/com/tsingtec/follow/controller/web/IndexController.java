package com.tsingtec.follow.controller.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import com.tsingtec.follow.entity.sys.Admin;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.service.sys.AdminService;
import com.tsingtec.follow.utils.RandomValidateCodeUtil;
import com.tsingtec.follow.vo.req.sys.login.LoginReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * 无需授权就可以进入的页面
 */
@Slf4j
@Controller
@Api(tags = "登录模块-错误模块")
public class IndexController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private WxMaService wxService;

    @Resource(name = "ehCacheManager")
    private EhCacheManager mycacheManager;

    @GetMapping(value = {"/","/login"})
    public String login(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            return "redirect:/home/index";
        };
        return "index/login";
    }

    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "index/login";
    }

    @GetMapping("/kaptcha")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            log.error("获取验证码失败>>>> ", e);
        }
    }

    /**
     * String scene, String page, int width, boolean autoColor, WxMaCodeLineColor lineColor, boolean isHyaline
     * @return
     * @throws WxErrorException
     */
    @ResponseBody
    @GetMapping(value = "/qrcode")
    @ApiOperation(value = "二维码登录接口")
    public void qrcode(HttpServletResponse response){
        Subject subject = SecurityUtils.getSubject();
        String sessionid = subject.getSession().getId().toString();
        try {
            byte[] buffer = wxService.getQrcodeService().createWxaCodeUnlimitBytes(sessionid.replace("-",""),"pages/auth/index",280,false,new WxMaCodeLineColor("0","0","0"),false);
            InputStream buffin = new ByteArrayInputStream(buffer);
            BufferedImage img;
            img = ImageIO.read(buffin);
            // 禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            // 将图像输出到Servlet输出流中。
            ServletOutputStream sos;
            sos = response.getOutputStream();
            ImageIO.write(img, "JPEG", sos);
            Cache<String, String> cache = mycacheManager.getCache("login");
            cache.put(sessionid.replace("-",""),sessionid);
            sos.close();
        } catch (IOException | WxErrorException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @GetMapping("/verify")
    public DataResult verify(HttpServletRequest request,HttpServletResponse response){
        Subject subject = SecurityUtils.getSubject();
        String sessionid = subject.getSession().getId().toString();
        Cache<String, String> cache = mycacheManager.getCache("agree");
        String phone = cache.get(sessionid.replace("-",""));
        if(null == phone){
            return DataResult.getResult(1,"请使用微信扫描二维码登录");
        }else {
            Admin admin = adminService.findByLoginName(phone);
            PrincipalCollection principals = new SimplePrincipalCollection(admin, admin.getLoginName());
            WebSubject.Builder builder = new WebSubject.Builder(request,response);
            builder.principals(principals);
            builder.authenticated(true);
            builder.sessionId(request.getSession().getId());
            subject = builder.buildWebSubject();
            ThreadContext.bind(subject);
            return DataResult.success();
        }
    }

    @ResponseBody
    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录接口")
    public DataResult login(@RequestBody @Valid LoginReqVO vo) {
        DataResult result = DataResult.success();

        Subject subject = SecurityUtils.getSubject();
        String code = (String)subject.getSession().getAttribute("vrifyCode");
        if(!StringUtils.hasText(code)||!code.equals(vo.getVercode())){
            result.setCode(400);
            result.setMsg("验证码错误,请重新输入");
            return result;
        }
        subject.logout();
        UsernamePasswordToken token = new UsernamePasswordToken(vo.getUsername(),vo.getPassword());
        try {
            subject.login(token);
            result.setCode(200);
            result.setMsg("登录成功");
        }catch (UnknownAccountException e) {
            result.setCode(400);
            result.setMsg("你被禁止登录了,不知道吗?");
        }catch(ExcessiveAttemptsException e1) {
            result.setCode(400);
            result.setMsg("尝试登录超过5次，账号已冻结，30分钟后再试");
        }catch(IncorrectCredentialsException e2) {
            result.setCode(400);
            result.setMsg("估计密码错误哦!");
        }catch(AccountException e0){
            result.setCode(400);
            result.setMsg("账号密码错误!");
        }
        return result;
    }

    @GetMapping("/403")
    public String error403(){
        return "error/403";
    }

    @GetMapping("/404")
    public String error404(){
        return "error/404";
    }

    @GetMapping("/500")
    public String error405(){
        return "error/500";
    }
}
