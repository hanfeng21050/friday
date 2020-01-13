package com.hf.friday.service.impl;

import com.hf.friday.base.Results;
import com.hf.friday.dao.RoleUserDao;
import com.hf.friday.dto.RoleDto;
import com.hf.friday.model.SysRole;
import com.hf.friday.model.SysRoleUser;
import com.hf.friday.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RoleUserServiceImpl implements RoleUserService {
    @Autowired
    private RoleUserDao roleUserDao;

    @Override
    public Results<SysRoleUser> getRoleUserByUserId(Integer roleId) {
        SysRoleUser roleUser = roleUserDao.getSysRoleUserByUserId(roleId);
        if(roleUser != null){
            return Results.success(roleUser);
        }else {
            return Results.success();
        }
    }
}
