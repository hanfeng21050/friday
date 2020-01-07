package com.hf.friday.controller;

import com.hf.friday.model.SysUser;
import com.hf.friday.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
@Slf4j
public class SysUserController {
    @Autowired
    private SysUserService userService;

    @RequestMapping("/{id}")
    public SysUser user(@PathVariable Long id)
    {
        log.info("UserController.user():parm (id = "+id+")");
        return userService.getUser(id);
    }
}
