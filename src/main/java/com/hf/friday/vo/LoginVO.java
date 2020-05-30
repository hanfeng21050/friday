package com.hf.friday.vo;

import com.hf.friday.model.SysUser;
import lombok.Data;

/**
 * @Author CoolWind
 * @Date 2020/4/30 10:01
 */
@Data
public class LoginVO{
    private String token;
    private SysUser user;
}
