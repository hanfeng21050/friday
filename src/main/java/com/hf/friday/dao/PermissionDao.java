package com.hf.friday.dao;

import com.hf.friday.model.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Component
public interface PermissionDao {

    @Select("select * from sys_permission t")
    public List<SysPermission> findAll();

    @Select("select p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permission_id where rp.role_id=#{roleId} order by p.sort")
    List<SysPermission> listAllPermissionByRoleId(Integer roleId);
}
