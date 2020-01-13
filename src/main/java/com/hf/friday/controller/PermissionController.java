package com.hf.friday.controller;

import com.alibaba.fastjson.JSONArray;
import com.hf.friday.base.Results;
import com.hf.friday.dto.RoleDto;
import com.hf.friday.model.SysPermission;
import com.hf.friday.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        System.out.println(permissionService.listAllPermissionByRoleId(roleDto.getId()));
        return permissionService.listAllPermissionByRoleId(roleDto.getId());
    }

}
