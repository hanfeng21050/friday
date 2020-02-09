package com.hf.friday.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RolePermissionDao {

    int save(@Param("roleId")Integer id, @Param("permissionIds") List<Long> permissionIds);

    @Delete("delete from sys_role_permission where role_id = #{roleId}")
    int deleteRolePermissionByRoleId(Integer roleId);

    @Delete("delete from sys_role_permission where permission_id = #{PermissionId}")
    int deleteRolePermissionByPermissionId(Integer PermissionId);

}
