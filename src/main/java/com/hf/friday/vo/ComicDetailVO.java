package com.hf.friday.vo;

import com.hf.friday.model.Chapter;
import com.hf.friday.model.Comic;
import com.hf.friday.model.Comment;
import lombok.Data;

import java.util.List;

/**
 * APP漫画详情页所需要的数据
 * @Author CoolWind
 * @Date 2020/4/18 17:42
 */
@Data
public class ComicDetailVO {
    private List<ComicVO> comicVOList;
    private Comic comic;
    private List<Chapter> chapterList;
    private List<Comment> comments;
}
