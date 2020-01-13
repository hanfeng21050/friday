package com.hf.friday.controller;

import com.hf.friday.base.PageTableRequest;
import com.hf.friday.base.ResponseCode;
import com.hf.friday.base.Results;
import com.hf.friday.dto.UserDto;
import com.hf.friday.model.SysUser;
import com.hf.friday.service.UserService;
import com.hf.friday.util.MD5;
import com.hf.friday.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.jws.WebResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

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
    public String addUserPage(Model model)
    {
        model.addAttribute(new SysUser());
        return "user/user-add";
    }


    @PostMapping("/add")
    @ResponseBody
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
        //给密码MD5加密
        userDto.setPassword(MD5.crypt(userDto.getPassword()));
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
    public String editUserPage(Model model,SysUser sysUser)
    {
        sysUser = userService.getUserById(sysUser.getId());
        System.out.println(sysUser);
        model.addAttribute(sysUser);
        return "user/user-edit";
    }

    @PostMapping("/edit")
    @ResponseBody
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
    public Results deleteUser(String ids)
    {
        log.info("UserController.deleteUser() param:(ids = "+ids+")");
        List<Integer> list = StringUtils.String2Int(ids);
        int count = userService.deleteUser(list);
        if(count == ids.length())
        {
            return Results.success();
        }else
        {
            return Results.failure();
        }
    }


    @GetMapping("/findUserByFuzzyUserName")
    @ResponseBody
    public Results findUserByFuzzyUserName(PageTableRequest request,String username)
    {
        log.info("UserController.findUserByFuzzyUserName() param:(request = "+request+" username:"+username+")");
        request.countOffset();
        return userService.findUserByFuzzyUserName(request.getOffset(),request.getLimit(),username);
    }

    //格式转换 json格式的日期到Date类型
    String pattern = "yyyy-MM-dd";
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat(pattern),true));
    }
}
