package com.hf.friday.dao;

import com.hf.friday.base.Results;
import com.hf.friday.model.SysVersionLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface VersionLogDao {

    @Select("select * from sys_version_log  order by create_time desc limit #{size}")
    List<SysVersionLog> listBySizeAndNew(Integer size);
}
