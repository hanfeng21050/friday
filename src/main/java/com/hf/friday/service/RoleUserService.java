package com.hf.friday.service;

import com.hf.friday.base.Results;
import com.hf.friday.dto.RoleDto;
import com.hf.friday.model.SysRole;
import com.hf.friday.model.SysRoleUser;

public interface RoleUserService {

    //通过角色id查询用户id
    public Results<SysRoleUser> getRoleUserByUserId(Integer roleId);

}
