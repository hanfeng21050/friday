package com.hf.friday.dao;

import com.hf.friday.model.SysFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FileDao {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_file(file_name, uuid_name, user_id,url, description, create_time, update_time) values(#{fileName},#{uuidName},#{userId},#{url}, #{description},now(), now())")
    void save(SysFile sysFile);

    @Select("select * from sys_file t where user_id = #{userId} order by t.id limit #{startPosition} , #{limit}")
    List<SysFile> getAllFilesByPageAndUserId(Integer startPosition, Integer limit,Integer userId);

    @Select("select count(*) from sys_file t where user_id = #{userId}")
    Integer countFilesByUserId(Integer userId);

    @Select("select * from sys_file where id = #{id}")
    SysFile findFileById(Integer id);
}
