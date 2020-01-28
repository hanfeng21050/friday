package com.hf.friday.service.impl;

import com.hf.friday.dao.PermissionDao;
import com.hf.friday.dto.LoginUser;
import com.hf.friday.model.SysUser;
import com.hf.friday.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.getUserByUsername(username);
        if(user == null)
        {
            throw  new AuthenticationCredentialsNotFoundException("用户不存在");
        }else if(user.getStatus() == SysUser.Status.LOCKED){
            throw  new AuthenticationCredentialsNotFoundException("用户被锁定，请联系管理员");
        } else if(user.getStatus() == SysUser.Status.VALID) {
            LoginUser loginUser = new LoginUser();
            //赋值
            BeanUtils.copyProperties(user,loginUser);
            loginUser.setPermissions(permissionDao.listByUserId(user.getId().intValue()));
            return loginUser;
        }
        return null;
    }
}
