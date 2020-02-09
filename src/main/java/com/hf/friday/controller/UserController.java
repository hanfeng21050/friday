package com.hf.friday.controller;

import com.hf.friday.base.PageTableRequest;
import com.hf.friday.base.ResponseCode;
import com.hf.friday.base.Results;
import com.hf.friday.dto.UserDto;
import com.hf.friday.model.SysUser;
import com.hf.friday.service.UserService;
import com.hf.friday.util.StringUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页查询
     * @param request
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "分页获取用户信息", notes = "分页获取用户信息")//描述
    @ApiImplicitParam(name = "request", value = "分页查询实体类", required=false)
    @PreAuthorize("hasAuthority('sys:user:query')")
    public Results<SysUser> list(PageTableRequest request)
    {
        request.countOffset();
        log.info("UserController.getAll() param:(request = "+request+")");
        return userService.list(request.getOffset(), request.getLimit());
    }

    /**
     * 跳转到新增页面
     * @param model
     * @return
     */
    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys:user:add')")
    @ApiOperation(value = "用户新增页面", notes = "跳转到新增用户信息页面")//描述
    public String addUserPage(Model model)
    {
        model.addAttribute(new SysUser());
        return "user/user-add";
    }


    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "保存用户信息", notes = "保存新增的用户信息")//描述
    @PreAuthorize("hasAuthority('sys:user:add')")
    public Results<SysUser> addUser(UserDto userDto,Integer roleId)
    {
        SysUser sysUser = null;
        //查询用户名是否已经存在
        sysUser = userService.getUserByUsername(userDto.getUsername());
        if(sysUser != null && !(sysUser.getId().equals(userDto.getId())))
        {
            return Results.failure(ResponseCode.USERNAME_REPEAT.getCode(),ResponseCode.USERNAME_REPEAT.getMessage());
        }

        //查询手机好是否已经存在
        sysUser = userService.getUserByPhone(userDto.getTelephone());
        if(sysUser != null && !(sysUser.getId().equals(userDto.getId())))
        {
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(),ResponseCode.PHONE_REPEAT.getMessage());
        }

        //查询邮箱是否已经存在
        sysUser = userService.getUserByEmail(userDto.getEmail());
        if(sysUser != null && !(sysUser.getId().equals(userDto.getId())))
        {
            return Results.failure(ResponseCode.EMAIL_REPEAT.getCode(),ResponseCode.EMAIL_REPEAT.getMessage());
        }

        //用户创建好后默认开启
        userDto.setStatus(1);
        //加密
        //userDto.setPassword(MD5.crypt(userDto.getPassword()));
        userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        log.info("UserController.getAll() param:(userDto = "+userDto+")");
        return userService.save(userDto,roleId);
    }

    /**
     * 跳转到editUser页面
     * @param model
     * @param sysUser
     * @return
     */
    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @ApiOperation(value = "用户编辑页面", notes = "跳转到用户信息编辑页面")//描述
    @ApiImplicitParam(name = "user", value = "用户实体类", dataType = "SysUser")
    public String editUserPage(Model model,SysUser sysUser)
    {
        sysUser = userService.getUserById(sysUser.getId());
        model.addAttribute(sysUser);
        return "user/user-edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @ApiOperation(value = "保存用户信息", notes = "保存编辑完的用户信息")//描述
    public Results editUser(UserDto userDto,Integer roleId)
    {
        SysUser sysUser = null;
        //查询用户名是否已经存在
        sysUser = userService.getUserByUsername(userDto.getUsername());
        if(sysUser != null && !(sysUser.getId().equals(userDto.getId())))
        {
            return Results.failure(ResponseCode.USERNAME_REPEAT.getCode(),ResponseCode.USERNAME_REPEAT.getMessage());
        }

        //查询手机好是否已经存在
        sysUser = userService.getUserByPhone(userDto.getTelephone());
        if(sysUser != null && !(sysUser.getId().equals(userDto.getId())))
        {
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(),ResponseCode.PHONE_REPEAT.getMessage());
        }

        //查询邮箱是否已经存在
        sysUser = userService.getUserByEmail(userDto.getEmail());
        if(sysUser != null && !(sysUser.getId().equals(userDto.getId())))
        {
            return Results.failure(ResponseCode.EMAIL_REPEAT.getCode(),ResponseCode.EMAIL_REPEAT.getMessage());
        }

        log.info("UserController.editUser() param:(userDto = "+userDto+" roleId:"+roleId+")");

        return userService.updateUser(userDto,roleId);
    }


    @GetMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:del')")
    @ApiOperation(value = "删除用户信息", notes = "删除用户信息 exp:1,2,3,")//描述
    @ApiImplicitParam(name = "ids", value = "用户id集合", required = true, dataType = "String")
    public Results deleteUser(String ids)
    {
        log.info("UserController.deleteUser() param:(ids = "+ids+")");
        List<Integer> list = StringUtil.String2Int(ids);
        int count = userService.deleteUser(list);
        if(count > 0)
        {
            return Results.success();
        }else
        {
            return Results.failure();
        }
    }


    @GetMapping("/findUserByFuzzyUserName")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:query')")
    @ApiOperation(value = "模糊查询用户信息", notes = "模糊搜索查询用户信息")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "模糊搜索的用户名", required = true),
    })
    public Results findUserByFuzzyUserName(PageTableRequest request,String username)
    {
        log.info("UserController.findUserByFuzzyUserName() param:(request = "+request+" username:"+username+")");
        request.countOffset();
        return userService.findUserByFuzzyUserName(request.getOffset(),request.getLimit(),username);
    }

    @PostMapping("/changePassword")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:password')")
    @ApiOperation(value = "修改密码", notes = "修改用户密码")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名", required = true),
            @ApiImplicitParam(name = "oldPassword",value = "旧密码", required = true),
            @ApiImplicitParam(name = "newPassword",value = "新密码", required = true),
    })
    public Results changePassword(String username,String oldPassword,String newPassword)
    {
        log.info("UserController.changePassword() param:(username = "+username+")");
        return userService.changePassword(username,oldPassword,newPassword);
    }


    @PostMapping("/switchStat")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @ApiOperation(value = "修改状态", notes = "修改用户状态")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status",value = "状态", required = true),
            @ApiImplicitParam(name = "id",value = "用户id", required = true),
    })
    public Results switchStat(Boolean status,Integer id)
    {
        log.info("UserController.changePassword() param:(id = "+id+",status="+status+")");
        return userService.switchStat(id,status);
    }

    //格式转换 json格式的日期到Date类型
    String pattern = "yyyy-MM-dd";
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat(pattern),true));
    }
}
