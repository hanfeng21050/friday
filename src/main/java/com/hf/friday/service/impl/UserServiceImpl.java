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

    @Override
    public SysUser getUserByPhone(String telephone) {
        return userDao.getUserByPhone(telephone);
    }

    @Override
    public SysUser getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public SysUser getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public SysUser getUserById(Long id) {

        return userDao.getUserById(id);
    }

    @Override
    public Results updateUser(UserDto userDto, Integer roleId) {
        if(roleId != null)
        {
            //更新user表
            userDao.updateUser(userDto);

            //更新roleuser表
            SysRoleUser roleUser = new SysRoleUser();
            roleUser.setUserId(userDto.getId().intValue());
            roleUser.setRoleId(roleId);
            if(roleUserDao.getSysRoleUserByUserId(userDto.getId().intValue()) != null)
            {
                roleUserDao.updateSysRoleUser(roleUser);
            }else
            {
                roleUserDao.save(roleUser);
            }
            return Results.success();
        }else {
            return Results.failure();
        }

    }
}
