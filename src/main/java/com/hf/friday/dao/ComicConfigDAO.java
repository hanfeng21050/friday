package com.hf.friday.dao;

import com.hf.friday.model.ComicConfig;
import com.hf.friday.model.ComicConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ComicConfigDAO {
    long countByExample(ComicConfigExample example);

    int deleteByExample(ComicConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ComicConfig record);

    int insertSelective(ComicConfig record);

    List<ComicConfig> selectByExample(ComicConfigExample example);

    ComicConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ComicConfig record, @Param("example") ComicConfigExample example);

    int updateByExample(@Param("record") ComicConfig record, @Param("example") ComicConfigExample example);

    int updateByPrimaryKeySelective(ComicConfig record);

    int updateByPrimaryKey(ComicConfig record);
}