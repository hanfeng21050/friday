package com.hf.friday.dao;

import com.hf.friday.dto.RoleDto;
import com.hf.friday.model.SysRole;
import com.hf.friday.model.SysUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RoleDao {

    @Select("select * from sys_role t")
    List<SysRole> getAllRoles();

    @Select("select * from sys_role t where t.name = #{name}")
    SysRole getRoleByName(String name);

    @Select("select * from sys_role t order by t.id limit #{startPosition} , #{limit}")
    List<SysRole> getAllRolesByPage(@Param("startPosition") Integer startPosition, @Param("limit") Integer limit);

    @Select("select count(*) from sys_role t")
    Long countAllRoles();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_role(name, description, create_time, update_time) values(#{name}, #{description},now(), now())")
    int save(SysRole role);

    @Select("select * from sys_role t where t.id = #{id}")
    SysUser getUserById(Long id);

    int updateRole(SysRole sysRole);

    @Select("select count(*) from sys_role t where t.name like '%${name}%'")
    Long getRoleCountByFuzzyName(@Param("name") String name);

    @Select("select * from sys_role t where t.name like '%${name}%' limit #{startPosition} , #{limit}")
    List<SysRole> getRoleByFuzzyNameByPage(@Param("name") String name, @Param("startPosition") Integer startPosition, @Param("limit") Integer limit);

    @Select("select * from sys_role where id = #{id}")
    SysRole getRoleById(Integer id);

    @Delete("delete from sys_role where id = #{id}")
    void deleteRole(int id);
}
