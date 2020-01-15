package com.hf.friday.controller;

import com.alibaba.fastjson.JSONArray;
import com.hf.friday.base.Results;
import com.hf.friday.dto.RoleDto;
import com.hf.friday.model.SysPermission;
import com.hf.friday.model.SysUser;
import com.hf.friday.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    @ResponseBody
    @RequestMapping("/listAllPermission")
    public Results<JSONArray> listAllPermission()
    {
        log.info("PermissionController.listAllPermission()");
        return permissionService.listAllPermission();
    }

    @GetMapping("/listAllPermissionByRoleId")
    @ResponseBody
    public Results<SysPermission> listAllPermissionByRoleId(RoleDto roleDto)
    {
        log.info("PermissionController.listAllPermissionByRoleId() param:(id="+roleDto.getId()+")");
        return permissionService.listAllPermissionByRoleId(roleDto.getId());
    }

    @GetMapping("/menuAll")
    @ResponseBody
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
    public String addPermissionPage(Model model,SysPermission sysPermission)
    {
        sysPermission = permissionService.findPermissionById(sysPermission.getId());
        model.addAttribute("sysPermission",sysPermission);
        return "permission/permission-add";
    }


    @PostMapping("/add")
    @ResponseBody
    public Results addPermission(@RequestBody SysPermission sysPermission)
    {
        log.info("PermissionController.addPermission() param:(sysPermission="+sysPermission+")");
        return permissionService.save(sysPermission);
    }

    @PostMapping("/edit")
    @ResponseBody
    public Results editPermission(@RequestBody SysPermission sysPermission)
    {
        log.info("PermissionController.editPermission() param:(sysPermission="+sysPermission+")");
        return permissionService.update(sysPermission);
    }

    @GetMapping("/delete")
    @ResponseBody
    public Results deletePermission(Integer id)
    {
        log.info("PermissionController.deletePermission() param:(id="+id+")");
        return permissionService.delete(id);
    }
}
