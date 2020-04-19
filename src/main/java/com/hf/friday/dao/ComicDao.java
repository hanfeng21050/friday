package com.hf.friday.dao;

import com.hf.friday.model.Comic;
import com.hf.friday.model.ComicExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ComicDAO {
    long countByExample(ComicExample example);

    int deleteByExample(ComicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Comic record);

    int insertSelective(Comic record);

    List<Comic> selectByExample(ComicExample example);

    Comic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Comic record, @Param("example") ComicExample example);

    int updateByExample(@Param("record") Comic record, @Param("example") ComicExample example);

    int updateByPrimaryKeySelective(Comic record);

    int updateByPrimaryKey(Comic record);
}