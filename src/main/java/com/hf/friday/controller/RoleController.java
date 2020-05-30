package com.hf.friday.controller;

import com.hf.friday.vo.HtpRquest;
import com.hf.friday.base.Results;
import com.hf.friday.dto.RoleDto;
import com.hf.friday.model.SysRole;
import com.hf.friday.service.RoleService;
import com.hf.friday.util.StringUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/all")
    @ResponseBody
    @ApiOperation(value = "获取所有角色", notes = "获取所有角色信息")//描述
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
    @ApiOperation(value = "分页获取角色", notes = "用户分页获取角色信息")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "limit", required = true,dataType = "Integer"),
    })
    public Results<SysRole> list(HtpRquest request)
    {
        request.countOffset();
        log.info("RoleController.list() param:(request = "+request+")");
        return roleService.list(request.getOffset(), request.getLimit());
    }

    /**
     * 跳转到新增页面
     * @param model
     * @return
     */
    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys:role:add')")
    @ApiOperation(value = "新增角色信息页面", notes = "跳转到角色信息新增页面")//描述
    public String addRolePage(Model model)
    {
        model.addAttribute(new SysRole());
        return "role/role-add";
    }


    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:add')")
    @ApiOperation(value = "保存角色信息", notes = "保存新增的角色信息")//描述
    @ApiImplicitParam(name = "roleDto",value = "角色信息实体类", required = true,dataType = "RoleDto")
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
    @ApiOperation(value = "编辑角色信息页面", notes = "跳转到角色信息编辑页面")//描述
    @ApiImplicitParam(name = "role",value = "角色信息实体类", required = true,dataType = "SysRole")
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
    @ApiOperation(value = "保存角色信息", notes = "保存被编辑的角色信息")//描述
    @ApiImplicitParam(name = "roleDto",value = "角色信息实体类", required = true,dataType = "RoleDto")
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
    @ApiOperation(value = "模糊查询角色信息", notes = "模糊搜索查询角色信息")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName",value = "模糊搜索的角色名", required = true),
    })
    public Results findRoleByFuzzyName(HtpRquest request, String roleName)
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
    @ApiOperation(value = "删除角色信息", notes = "删除角色信息 exp:1,2,3,")//描述
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

    String pattern = "yyyy-MM-dd";
    //只需要加上下面这段即可，注意不能忘记注解
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));// CustomDateEditor为自定义日期编辑器
    }
}
