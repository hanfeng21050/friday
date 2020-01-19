package com.hf.friday.controller;

import com.alibaba.fastjson.JSONArray;
import com.hf.friday.base.Results;
import com.hf.friday.dto.RoleDto;
import com.hf.friday.model.SysPermission;
import com.hf.friday.model.SysUser;
import com.hf.friday.service.PermissionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;

@Controller
@RequestMapping("/permission")
@Slf4j
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "获取所有权限值", notes = "获取所有菜单的权限值")//描述
    @ResponseBody
    @RequestMapping("/listAllPermission")
    @PreAuthorize("hasAnyAuthority('sys:menu:query','sys:role:add','sys:role:edit')")
    public Results<JSONArray> listAllPermission()
    {
        log.info("PermissionController.listAllPermission()");
        return permissionService.listAllPermission();
    }

    @GetMapping("/listAllPermissionByRoleId")
    @ResponseBody
    @ApiOperation(value = "获取角色权限", notes = "根据角色Id去查询拥有的权限")//描述
    public Results<SysPermission> listAllPermissionByRoleId(RoleDto roleDto)
    {
        log.info("PermissionController.listAllPermissionByRoleId() param:(id="+roleDto.getId()+")");
        return permissionService.listAllPermissionByRoleId(roleDto.getId());
    }

    @GetMapping("/menuAll")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:menu:query')")
    @ApiOperation(value = "获取所有权限值", notes = "获取所有菜单的权限值")//描述
    public Results getMenuAll()
    {
        log.info("PermissionController.getMenuAll() param:(null)");
        return permissionService.getMenuAll();
    }

    /**
     * 跳转到add页面
     * @param model
     * @return
     */
    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys:menu:add')")
    @ApiOperation(value = "新增页面", notes = "跳转到菜单信息新增页面")//描述
    public String addPermissionPage(Model model)
    {
        SysPermission sysPermission = new SysPermission();
        model.addAttribute(sysPermission);
        return "permission/permission-add";
    }

    /**
     * 跳转到edit页面
     * @param model
     * @param sysPermission
     * @return
     */
    @GetMapping("/edit")
    @ApiOperation(value = "编辑页面", notes = "跳转到菜单信息编辑页面")//描
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    public String addPermissionPage(Model model,SysPermission sysPermission)
    {
        sysPermission = permissionService.findPermissionById(sysPermission.getId());
        model.addAttribute("sysPermission",sysPermission);
        return "permission/permission-add";
    }


    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:menu:add')")
    @ApiOperation(value = "添加菜单", notes = "保存用户新增的菜单信息")//描述
    @ApiImplicitParam(name = "sysPermission", value = "菜单权限实体sysPermission", required = true, dataType = "SysPermission")
    public Results addPermission(@RequestBody SysPermission sysPermission)
    {
        log.info("PermissionController.addPermission() param:(sysPermission="+sysPermission+")");
        return permissionService.save(sysPermission);
    }

    @PostMapping("/edit")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    @ApiOperation(value = "更新菜单信息", notes = "保存用户编辑完的菜单信息")//描述
    @ApiImplicitParam(name = "sysPermission", value = "菜单权限实体sysPermission", required = true, dataType = "SysPermission")
    public Results editPermission(@RequestBody SysPermission sysPermission)
    {
        log.info("PermissionController.editPermission() param:(sysPermission="+sysPermission+")");
        return permissionService.update(sysPermission);
    }

    @GetMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:menu:del')")
    @ApiOperation(value = "删除菜单", notes = "根据菜单Id去删除菜单")//描述
    public Results deletePermission(Integer id)
    {
        log.info("PermissionController.deletePermission() param:(id="+id+")");
        return permissionService.delete(id);
    }

    @GetMapping("/menu")
    @ResponseBody
    @ApiOperation(value = "获取菜单", notes = "获取用户所属角色下能显示的菜单")//描述
    @ApiImplicitParam(name = "userId", value = "userId", required = true, dataType = "Long")
    public Results getMenuByUserId(Integer userId)
    {
        log.info("PermissionController.getMenuByUserId() param:(userId="+userId+")");
        return permissionService.getMenuByUserId(userId);
    }
}
