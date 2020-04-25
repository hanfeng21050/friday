package com.hf.friday.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolePermission implements Serializable {
    private Integer roleId;
    private Integer permissionId;

}
