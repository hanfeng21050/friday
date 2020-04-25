package com.hf.friday.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleUser implements Serializable {
    private Integer userId;
    private Integer roleId;

}
