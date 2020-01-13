package com.hf.friday.dto;

import com.hf.friday.model.SysRole;
import lombok.Data;

import java.util.List;
@Data
public class RoleDto extends SysRole {
    private List<Long> permissionIds;
}
