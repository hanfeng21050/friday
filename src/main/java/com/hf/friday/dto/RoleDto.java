package com.hf.friday.dto;

import com.hf.friday.model.SysRole;
import lombok.Data;

import java.util.List;
public class RoleDto extends SysRole {
    private List<Long> permissionIds;

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "name=" + getName()+
                ",description=" + getDescription()+
                ",permissionIds=" + permissionIds +
                '}';
    }
}
