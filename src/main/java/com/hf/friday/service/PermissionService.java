package com.hf.friday.service;

import com.alibaba.fastjson.JSONArray;
import com.hf.friday.base.Results;
import com.hf.friday.dto.RoleDto;
import com.hf.friday.model.SysPermission;

public interface PermissionService {

    //返回菜单树
    Results<JSONArray> listAllPermission();

    //查询roleId所对应的权限
    Results<SysPermission> listAllPermissionByRoleId(Integer roleId);

    //查询所有权限
    Results<SysPermission> getMenuAll();

    //新保存权限
    Results save(SysPermission sysPermission);

    //根据id查询
    SysPermission findPermissionById(Integer id);

    //更新permission信息
    Results update(SysPermission sysPermission);

    //删除权限
    Results delete(Integer id);

    //根据用户权限返回菜单
    Results getMenuByUserId(Integer userId);
}
