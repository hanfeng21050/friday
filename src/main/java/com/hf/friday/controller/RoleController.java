package com.hf.friday.controller;

import com.hf.friday.base.Results;
import com.hf.friday.model.SysRole;
import com.hf.friday.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/all")
    @ResponseBody
    public Results<SysRole> getAll()
    {
        log.info("RoleController.getAll()");
        return roleService.getAll();
    }
}
