package com.hf.friday.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RolePermissionDao {

    int save(@Param("roleId")Integer id, @Param("permissionIds") List<Long> permissionIds);
}
