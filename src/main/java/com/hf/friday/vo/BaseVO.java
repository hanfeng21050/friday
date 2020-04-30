package com.hf.friday.vo;

import com.hf.friday.model.SysUser;
import lombok.Data;

/**
 * @Author CoolWind
 * @Date 2020/4/29 20:36
 */
@Data
public class BaseVO {
    private String token;//登录验证
    private Integer id;
}
