package com.hf.friday.dao;

import com.hf.friday.model.ComicType;
import com.hf.friday.model.ComicTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ComicTypeDAO {
    long countByExample(ComicTypeExample example);

    int deleteByExample(ComicTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ComicType record);

    int insertSelective(ComicType record);

    List<ComicType> selectByExample(ComicTypeExample example);

    ComicType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ComicType record, @Param("example") ComicTypeExample example);

    int updateByExample(@Param("record") ComicType record, @Param("example") ComicTypeExample example);

    int updateByPrimaryKeySelective(ComicType record);

    int updateByPrimaryKey(ComicType record);
}