package com.hf.friday.service.impl;

import com.hf.friday.base.Results;
import com.hf.friday.dao.RoleUserDao;
import com.hf.friday.dao.UserDao;
import com.hf.friday.dto.UserDto;
import com.hf.friday.model.SysRole;
import com.hf.friday.model.SysRoleUser;
import com.hf.friday.model.SysUser;
import com.hf.friday.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleUserDao roleUserDao;


    @Override
    public Results<SysUser> list(Integer offset, Integer limit) {
        return Results.success(userDao.countAllUsers().intValue(),userDao.getAllUsersByPage(offset,limit));
    }

    @Override
    public Results<SysUser> save(UserDto userDto, Integer roleId) {
        if(roleId != null){

            //保存user表
            userDao.save(userDto);

            // 保存user-role表
            SysRoleUser roleUser = new SysRoleUser();
            roleUser.setRoleId(roleId);
            roleUser.setUserId(userDto.getId().intValue());
            roleUserDao.save(roleUser);
            return Results.success();
        }
        return Results.failure();
    }
}
