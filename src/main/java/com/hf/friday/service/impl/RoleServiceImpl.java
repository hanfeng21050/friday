package com.hf.friday.service.impl;

import com.hf.friday.base.Results;
import com.hf.friday.dao.RoleDao;
import com.hf.friday.dao.RolePermissionDao;
import com.hf.friday.dto.RoleDto;
import com.hf.friday.model.SysRole;
import com.hf.friday.model.SysUser;
import com.hf.friday.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public Results<SysRole> getAll() {
        return Results.success(50,roleDao.getAllRoles());
    }

    @Override
    public Results<SysRole> list(Integer offset, Integer limit) {
        return Results.success(roleDao.countAllRoles().intValue(),roleDao.getAllRolesByPage(offset,limit));
    }

    @Override
    public Results<SysRole> save(RoleDto roleDto) {
        //1. 保存角色
        roleDao.save(roleDto);

        //移除0
        List<Long> permissionIds = roleDto.getPermissionIds();
        permissionIds.remove(0L);

        //保存角色对应的权限
        if(!CollectionUtils.isEmpty(permissionIds))
        {
            rolePermissionDao.save(roleDto.getId(),permissionIds);
        }

        return Results.success();
    }

    @Override
    public SysUser getRoleByName(String name) {
        return null;
    }

    @Override
    public SysUser getRoleById(Long id) {
        return null;
    }

    @Override
    public int deleteRole(List<Integer> list) {
        int count = 0;
        for (Integer id : list) {
            //删除中间表
            rolePermissionDao.deleteRolePermissionByRoleId(id.intValue());
            //删除角色表
            roleDao.deleteRole(id.intValue());
            count++;
        }

        return count;
    }

    @Override
    public Results findRoleByFuzzyName(Integer offset, Integer limit, String roleName) {
        int count = roleDao.getRoleCountByFuzzyName(roleName).intValue();
        List<SysRole> roles = roleDao.getRoleByFuzzyNameByPage(roleName,offset,limit);
        return Results.success(count,roles);
    }

    @Override
    public SysRole getRoleById(Integer id) {
        return roleDao.getRoleById(id);
    }

    @Override
    public int update(RoleDto roleDto) {
        List<Long> permissionIds = roleDto.getPermissionIds();
        permissionIds.remove(0L);
        //更新前删除该角色所有的权限信息
        rolePermissionDao.deleteRolePermissionByRoleId(roleDto.getId());

        //添加权限
        if(!CollectionUtils.isEmpty(permissionIds))
        {
            rolePermissionDao.save(roleDto.getId(),permissionIds);
        }

        return roleDao.updateRole(roleDto);
    }
}
