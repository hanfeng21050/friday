package com.hf.friday.dao;

import com.hf.friday.model.SwiperImg;
import com.hf.friday.model.SwiperImgExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SwiperImgDAO {
    long countByExample(SwiperImgExample example);

    int deleteByExample(SwiperImgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SwiperImg record);

    int insertSelective(SwiperImg record);

    List<SwiperImg> selectByExample(SwiperImgExample example);

    SwiperImg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SwiperImg record, @Param("example") SwiperImgExample example);

    int updateByExample(@Param("record") SwiperImg record, @Param("example") SwiperImgExample example);

    int updateByPrimaryKeySelective(SwiperImg record);

    int updateByPrimaryKey(SwiperImg record);
}