package com.tsingtec.follow.controller.web.sys;

import com.tsingtec.follow.entity.sys.Admin;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.handler.annotation.LogAnnotation;
import com.tsingtec.follow.service.sys.AdminService;
import com.tsingtec.follow.utils.HttpContextUtils;
import com.tsingtec.follow.vo.req.sys.admin.*;
import com.tsingtec.follow.vo.resp.sys.admin.AdminRoleRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/manager")
@Api(tags = "组织模块-用户管理")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @GetMapping("/admin")
    @RequiresPermissions("sys:admin:list")
    @ApiOperation(value = "分页获取用户列表接口")
    @LogAnnotation(title = "用户管理", action = "分页获取用户列表")
    public DataResult<Page<Admin>> pageInfo(AdminPageReqVO vo) {
        return DataResult.success(adminService.pageInfo(vo));
    }

    @PostMapping("/admin")
    @ApiOperation(value = "新增用户接口")
    @RequiresPermissions("sys:admin:add")
    @LogAnnotation(title = "用户管理",action = "新增用户")
    public DataResult addUser(@RequestBody @Valid AdminAddReqVO vo){
        adminService.insert(vo);
        return DataResult.success();
    }


    @PutMapping("/admin")
    @ApiOperation(value = "修改用户接口")
    @RequiresPermissions("sys:admin:update")
    @LogAnnotation(title = "用户管理",action = "修改用户")
    public DataResult update(@RequestBody AdminUpdateReqVO vo){
        Admin admin = HttpContextUtils.getAdmin();
        if(!admin.getId().equals(1) && vo.getId().equals(1)){
            return DataResult.fail("您没有权限对此账号进行此操作");
        }
        adminService.update(vo);
        return DataResult.success();
    }

    @PutMapping("/admin/psd")
    @ApiOperation(value = "修改用户密码接口")
    @LogAnnotation(title = "用户管理",action = "修改用户密码")
    public DataResult updatePwd(@RequestBody AdminPwdReqVO vo){
        Admin admin = HttpContextUtils.getAdmin();
        adminService.updatePwd(admin.getId(),vo);
        return DataResult.success();
    }

    /**
     * 不能删除admin 账号
     * @param aids
     * @return
     */
    @DeleteMapping("/admin")
    @ApiOperation(value = "删除用户接口")
    @RequiresPermissions("sys:admin:delete")
    @LogAnnotation(title = "用户管理",action = "删除用户")
    public DataResult deletedUser(@RequestBody @ApiParam(value = "用户id集合") List<Integer> aids){
        if(aids.contains(1)){
            aids.remove(aids.indexOf(1));
        }
        adminService.deleteBatch(aids);
        return DataResult.success();
    }

    /**
     * 非admin 账号无法获取到超级权限
     * @param id
     * @return
     */
    @GetMapping("/admin/role")
    @RequiresPermissions("sys:admin:getrole")
    @ApiOperation(value = "赋予角色-获取所有角色接口")
    @LogAnnotation(title = "用户管理",action = "赋予角色-获取所有角色接口")
    public DataResult<AdminRoleRespVO> getUserOwnRole(Integer id){
        return DataResult.success(adminService.getAdminRole(id));
    }

    /**
     * 非超级账号无法修改admin账号权限
     * @param vo
     * @return
     */
    @PutMapping("/admin/role")
    @RequiresPermissions("sys:admin:setrole")
    @ApiOperation(value = "赋予角色-用户赋予角色接口")
    @LogAnnotation(title = "用户管理",action = "赋予角色-用户赋予角色接口")
    public DataResult setUserOwnRole(@RequestBody AdminRoleOperationReqVO vo){
        Admin admin = HttpContextUtils.getAdmin();
        if(!admin.getId().equals(1)&& vo.getAid().equals(1)){
            return DataResult.fail("您没有权限对此账号进行此操作");
        }
        adminService.setAdminRole(vo.getAid(),vo.getRids());
        return DataResult.success();
    }
}
