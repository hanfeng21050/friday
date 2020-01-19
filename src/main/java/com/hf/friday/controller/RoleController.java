package com.hf.friday.controller;

import com.hf.friday.base.PageTableRequest;
import com.hf.friday.base.Results;
import com.hf.friday.dto.RoleDto;
import com.hf.friday.model.SysRole;
import com.hf.friday.service.RoleService;
import com.hf.friday.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasAuthority('sys:role:query')")
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
    @PreAuthorize("hasAuthority('sys:role:add')")
    public String addRolePage(Model model)
    {
        model.addAttribute(new SysRole());
        return "role/role-add";
    }


    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:add')")
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
    @PreAuthorize("hasAuthority('sys:role:edit')")
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
    @PreAuthorize("hasAuthority('sys:role:edit')")
    public Results edit(@RequestBody RoleDto roleDto)
    {

        log.info("RoleController.edit() param:(roleDto = "+roleDto+")");
        int count =  roleService.update(roleDto);
        if(count >= 1)
        {
            return Results.success();
        }else {
            return Results.failure();
        }
    }


    /**
     * 模糊查询
     * @param request
     * @param roleName
     * @return
     */
    @GetMapping("/findRoleByFuzzyName")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:query')")
    public Results findRoleByFuzzyName(PageTableRequest request,String roleName)
    {
        log.info("RoleController.findRoleByFuzzyName() param:(request = "+request+" ,roleName:"+roleName+")");
        request.countOffset();
        return roleService.findRoleByFuzzyName(request.getOffset(),request.getLimit(),roleName);
    }

    /**
     * 删除角色
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:del')")
    public Results deleteRole(String ids)
    {
        log.info("RoleController.deleteRole() param:(ids = "+ids+")");
        List<Integer> list = StringUtil.String2Int(ids);
        int count = roleService.deleteRole(list);
        if(count == ids.length())
        {
            return Results.success();
        }else
        {
            return Results.failure();
        }
    }
}
