package com.hf.friday.service;

import com.hf.friday.base.Results;
import com.hf.friday.dto.RoleDto;
import com.hf.friday.dto.UserDto;
import com.hf.friday.model.SysRole;
import com.hf.friday.model.SysUser;

import java.util.List;

public interface RoleService {

    public Results<SysRole> getAll();


    //返回角色的列表(分页)
    public Results<SysRole> list(Integer offset, Integer limit);

    //保存角色
    public Results<SysRole> save(RoleDto role);

    //通过用户名查询用户
    public SysUser getRoleByName(String name);

    //通过id查询角色
    public SysUser getRoleById(Long id);

    //删除角色
    public int deleteRole(List<Integer> list);

    //模糊查询（根据角色）
    public Results findRoleByFuzzyName(Integer offset, Integer limit, String roleName);

    //根据id查询角色
    SysRole getRoleById(Integer id);

    //更新角色及权限
    int update(RoleDto roleDto);
}
