package com.hf.friday.dao;

import com.hf.friday.model.ComicTag;
import com.hf.friday.model.ComicTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ComicTagDAO {
    long countByExample(ComicTagExample example);

    int deleteByExample(ComicTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ComicTag record);

    int insertSelective(ComicTag record);

    List<ComicTag> selectByExample(ComicTagExample example);

    ComicTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ComicTag record, @Param("example") ComicTagExample example);

    int updateByExample(@Param("record") ComicTag record, @Param("example") ComicTagExample example);

    int updateByPrimaryKeySelective(ComicTag record);

    int updateByPrimaryKey(ComicTag record);
}