package com.hf.friday.service.impl;

import com.hf.friday.dao.UserDao;
import com.hf.friday.model.SysUser;
import com.hf.friday.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private UserDao userDao;

    @Override
    public SysUser getUser(Long id) {
        return userDao.getUserById(id);
    }
}
