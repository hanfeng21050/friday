package com.hf.friday.dao;

import com.hf.friday.model.Image;
import com.hf.friday.model.ImageExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ImageDAO {
    long countByExample(ImageExample example);

    int deleteByExample(ImageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Image record);

    int insertSelective(Image record);

    List<Image> selectByExample(ImageExample example);

    Image selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Image record, @Param("example") ImageExample example);

    int updateByExample(@Param("record") Image record, @Param("example") ImageExample example);

    int updateByPrimaryKeySelective(Image record);

    int updateByPrimaryKey(Image record);

    /**
     * 查询一个章节的图片,且该章节状态为1
     * @param id
     * @return
     */
    @Select("SELECT * from image where image.target_id = (select chapter.id from chapter WHERE  chapter.status = 1 and chapter.id = #{id}) ORDER BY image.url ASC LIMIT #{offset} ,#{limit}")
    List<Image> listByChapterId(Integer id,Integer offset,Integer limit);
}