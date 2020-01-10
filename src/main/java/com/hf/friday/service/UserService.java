package com.hf.friday.service;

import com.hf.friday.base.Results;
import com.hf.friday.dto.UserDto;
import com.hf.friday.model.SysUser;

import java.util.List;

public interface UserService {
    //返回用户的列表(分页)
    public Results<SysUser> list(Integer offset, Integer limit);

    //保存用户
    public Results<SysUser> save(UserDto userDto, Integer roleId);

    //通过手机号查询用户
    public SysUser getUserByPhone(String telephone);

    //通过用户名查询用户
    public SysUser getUserByUsername(String username);

    //通过邮箱查询用户
    public SysUser getUserByEmail(String email);

    //通过id查询用户
    public SysUser getUserById(Long id);

    //更新用户
    public Results updateUser(UserDto userDto, Integer roleId);

    //删除用户
    public int deleteUser(Long id);
}
