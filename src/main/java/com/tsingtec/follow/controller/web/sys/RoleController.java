package com.tsingtec.follow.controller.web.sys;

import com.tsingtec.follow.entity.sys.Role;
import com.tsingtec.follow.exception.DataResult;
import com.tsingtec.follow.handler.annotation.LogAnnotation;
import com.tsingtec.follow.service.sys.RoleService;
import com.tsingtec.follow.vo.req.sys.role.RoleAddReqVO;
import com.tsingtec.follow.vo.req.sys.role.RolePageReqVO;
import com.tsingtec.follow.vo.req.sys.role.RoleUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/manager")
@Api(tags = "组织模块-角色管理")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/role")
    @ApiOperation(value = "新增角色接口")
    @LogAnnotation(title = "角色管理",action = "新增角色")
    public DataResult<Role> addRole(@RequestBody @Valid RoleAddReqVO vo){
        return DataResult.success(roleService.addRole(vo));
    }

    @DeleteMapping("/role")
    @ApiOperation(value = "删除角色接口")
    @RequiresPermissions("sys:role:delete")
    @LogAnnotation(title = "角色管理",action = "删除角色")
    public DataResult deletedUser(@RequestBody @ApiParam(value = "id集合") List<Integer> rids){
        roleService.deleteBatch(rids);
        return DataResult.success();
    }


    @PutMapping("/role")
    @ApiOperation(value = "更新角色信息接口")
    @LogAnnotation(title = "角色管理",action = "更新角色信息")
    public DataResult updateDept(@RequestBody @Valid RoleUpdateReqVO vo){
        roleService.updateRole(vo);
        return DataResult.success();
    }

    @GetMapping("/role/{id}")
    @ApiOperation(value = "查询角色详情接口")
    @LogAnnotation(title = "角色管理",action = "查询角色详情")
    public DataResult<Role> detailInfo(@PathVariable("id") Integer id){
        return DataResult.success(roleService.findById(id));
    }

    @GetMapping("/roles")
    @ApiOperation(value = "分页获取角色信息接口")
    @LogAnnotation(title = "角色管理",action = "分页获取角色信息")
    public DataResult<Page<Role>> pageInfo(RolePageReqVO vo){
        return DataResult.success(roleService.pageInfo(vo));
    }
}
