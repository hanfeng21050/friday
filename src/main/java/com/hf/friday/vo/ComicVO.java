package com.hf.friday.vo;

import com.hf.friday.model.Chapter;
import com.hf.friday.model.Comic;
import com.hf.friday.model.Tag;
import com.hf.friday.model.Type;
import lombok.Data;

import java.util.List;

/**
 * APP展示漫画所需要的数据
 * @Author CoolWind
 * @Date 2020/4/18 20:04
 */
@Data
public class ComicVO {
    private Comic comic;
    private Chapter chapter;//最新章节
}
