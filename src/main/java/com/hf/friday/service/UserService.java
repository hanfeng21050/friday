package com.hf.friday.service;

import com.hf.friday.base.Results;
import com.hf.friday.dto.UserDto;
import com.hf.friday.model.SysUser;

import java.util.List;

public interface UserService {
    //返回用户的列表(分页)
    public Results<SysUser> list(Integer offset, Integer limit);

    public Results<SysUser> save(UserDto userDto, Integer roleId);
}
