package com.hf.friday.vo;

import com.hf.friday.model.*;
import lombok.Data;

import java.util.List;

/**
 * APP漫画详情页所需要的数据
 * @Author CoolWind
 * @Date 2020/4/18 17:42
 */
@Data
public class ComicDetailVO extends BaseVO{
    private List<ComicVO> comicVOList;
    private Comic comic;
    private List<Chapter> chapterList;
    private List<Tag> tagList;
    private List<Type> typeList;
    private String host;
    private Boolean isCollect;
}
