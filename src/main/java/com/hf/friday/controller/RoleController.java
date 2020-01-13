package com.hf.friday.controller;

import com.hf.friday.base.PageTableRequest;
import com.hf.friday.base.ResponseCode;
import com.hf.friday.base.Results;
import com.hf.friday.dto.RoleDto;
import com.hf.friday.dto.UserDto;
import com.hf.friday.model.SysRole;
import com.hf.friday.model.SysUser;
import com.hf.friday.service.RoleService;
import com.hf.friday.util.MD5;
import com.hf.friday.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/all")
    @ResponseBody
    public Results<SysRole> All()
    {
        log.info("RoleController.All()");
        return roleService.getAll();
    }



    /**
     * 分页查询
     * @param request
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Results<SysRole> list(PageTableRequest request)
    {
        request.countOffset();
        log.info("RoleController.getAll() param:(request = "+request+")");
        return roleService.list(request.getOffset(), request.getLimit());
    }

    /**
     * 跳转到新增页面
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String addRolePage(Model model)
    {
        model.addAttribute(new SysRole());
        return "role/role-add";
    }


    @PostMapping("/add")
    @ResponseBody
    public Results<SysRole> addRole(@RequestBody RoleDto roleDto)
    {

        log.info("RoleController.addRole() param:(roleDto = "+roleDto+")");
        return roleService.save(roleDto);
    }


    /**
     * 跳转到修改页面
     * @param model
     * @return
     */
    @GetMapping("/edit")
    public String addEditPage(Model model,SysRole role)
    {

        model.addAttribute("sysRole",roleService.getRoleById(role.getId()));
        return "role/role-edit";
    }



    /**
     * 修改
     * @param roleDto
     * @return
     */
    @PostMapping("/edit")
    @ResponseBody
    public Results addEditPage(RoleDto roleDto)
    {
        return null;
    }

    /**
     * 模糊查询
     * @param request
     * @param roleName
     * @return
     */
    @GetMapping("/findRoleByFuzzyName")
    @ResponseBody
    public Results findRoleByFuzzyName(PageTableRequest request,String roleName)
    {
        log.info("RoleController.findRoleByFuzzyName() param:(request = "+request+" ,roleName:"+roleName+")");
        request.countOffset();
        return roleService.findRoleByFuzzyName(request.getOffset(),request.getLimit(),roleName);
    }
}
