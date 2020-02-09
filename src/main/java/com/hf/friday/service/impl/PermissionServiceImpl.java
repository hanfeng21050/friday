package com.hf.friday.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.hf.friday.base.Results;
import com.hf.friday.dao.PermissionDao;
import com.hf.friday.dao.RolePermissionDao;
import com.hf.friday.dto.RoleDto;
import com.hf.friday.model.SysPermission;
import com.hf.friday.service.PermissionService;
import com.hf.friday.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public Results<JSONArray> listAllPermission() {
        List<SysPermission> datas = permissionDao.findAll();
        JSONArray array = new JSONArray();
        TreeUtil.setPermissionsTree(0,datas,array);
        return Results.success(array);
    }

    @Override
    public Results listAllPermissionByRoleId(Integer roleId) {

        return Results.success(0,permissionDao.listAllPermissionByRoleId(roleId));
    }

    @Override
    public Results<SysPermission> getMenuAll() {
        return Results.success(0,permissionDao.findAll());
    }

    @Override
    public Results save(SysPermission sysPermission) {
        return (permissionDao.save(sysPermission) > 0 ? Results.success():Results.failure());
    }

    @Override
    public SysPermission findPermissionById(Integer id) {
        return permissionDao.findPermissionById(id);
    }

    @Override
    public Results update(SysPermission sysPermission) {
        return (permissionDao.updatePermission(sysPermission) > 0 ? Results.success() : Results.failure());
    }

    @Override
    public Results delete(Integer id) {
        //移除与该权限相关联的角色-权限中间表信息
        rolePermissionDao.deleteRolePermissionByPermissionId(id);
        return permissionDao.deletePemission(id) > 0 ? Results.success() : Results.failure();
    }

    @Override
    public Results getMenuByUserId(Integer userId) {
        List<SysPermission> datas = permissionDao.listByUserId(userId);
        datas = datas.stream().filter(p -> p.getType().equals(1)).collect(Collectors.toList());
        JSONArray array = new JSONArray();
        TreeUtil.setPermissionsTree(0,datas,array);
        return Results.success(array);
    }
}
