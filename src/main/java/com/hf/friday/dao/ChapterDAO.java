package com.hf.friday.dao;

import com.hf.friday.model.Chapter;
import com.hf.friday.model.ChapterExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ChapterDAO {
    long countByExample(ChapterExample example);

    int deleteByExample(ChapterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Chapter record);

    int insertSelective(Chapter record);

    List<Chapter> selectByExample(ChapterExample example);

    Chapter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Chapter record, @Param("example") ChapterExample example);

    int updateByExample(@Param("record") Chapter record, @Param("example") ChapterExample example);

    int updateByPrimaryKeySelective(Chapter record);

    int updateByPrimaryKey(Chapter record);

    @Select("select * from chapter where comic_id=#{id} limit #{startpage}, #{limit}")
    List<Chapter> list(int id,int startpage,int limit);

    /**
     * 获取漫画的所有章节
     * @param id
     * @return
     */
    @Select("select * from chapter where comic_id = #{id} and status =1  order by index asc")
    List<Chapter> listByComicId(int id);

    /**
     * 获取最新章节
     */
    @Select("select * from chapter where comic_id=#{id} and status =1  order by create_time desc limit 1")
    Chapter selectNew(int id);

}