package com.hf.friday.dao;

import com.hf.friday.base.Results;
import com.hf.friday.model.SysFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ImageDao {

    @Select("select count(*) from sys_file")
    Integer findImageCountAll();

    @Select("select * from sys_file limit #{offset},#{limit}")
    List<SysFile> findImageAll(Integer offset, Integer limit);

    @Select("select * from sys_file where id = #{id}")
    SysFile findImageById(Integer id);

    @Select("select count(*) from sys_file where id <= #{id}")
    Integer findIndex(Integer id);
}
