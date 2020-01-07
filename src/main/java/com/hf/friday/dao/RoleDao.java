package com.hf.friday.dao;

import com.hf.friday.model.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RoleDao {

    @Select("select * from sys_role t")
    List<SysRole> getAllRoles();
}
