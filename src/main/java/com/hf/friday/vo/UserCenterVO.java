package com.hf.friday.vo;

import com.hf.friday.model.SysUser;
import lombok.Data;

/**
 * 用户中心所需要的新
 * @Author CoolWind
 * @Date 2020/5/19 17:47
 */
@Data
public class UserCenterVO extends BaseVO{
    private SysUser sysUser;
}
