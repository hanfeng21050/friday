package com.hf.friday.service;

import com.hf.friday.base.Results;
import com.hf.friday.dto.UserDto;
import com.hf.friday.model.SysUser;

import java.util.List;

public interface UserService {
    //返回用户的列表(分页)
    Results<SysUser> list(Integer offset, Integer limit);

    //保存用户
    Results<SysUser> save(UserDto userDto, Integer roleId);

    //通过手机号查询用户
    SysUser getUserByPhone(String telephone);

    //通过用户名查询用户
    SysUser getUserByUsername(String username);

    //通过邮箱查询用户
    SysUser getUserByEmail(String email);

    //通过id查询用户
    public SysUser getUserById(Long id);

    //更新用户
    public Results updateUser(UserDto userDto, Integer roleId);

    //删除用户
    public int deleteUser(List<Integer> list);

    //模糊查询（根据用户名）
    public Results findUserByFuzzyUserName(Integer offset, Integer limit, String username);

    //修改密码
    Results changePassword(String username, String oldPassword, String newPassword);

    //修改状态
    Results switchStat(Integer id, Boolean status);
}
