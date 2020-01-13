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
}
