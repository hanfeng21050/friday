package com.hf.friday.controller;

import com.hf.friday.base.Results;
import com.hf.friday.model.SysRoleUser;
import com.hf.friday.service.RoleUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/roleuser")
@Slf4j
public class RoleUserController {

    @Autowired
    RoleUserService roleUserService;

    @PostMapping("/getRoleUserByUserId")
    @ResponseBody
    public Results getRoleUserByUserId(Integer userId)
    {
        log.info("RoleUserController.getRoleUserByUserId() param:(userId = "+userId+")");
        return roleUserService.getRoleUserByUserId(userId);
    }

}
