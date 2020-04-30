package com.hf.friday.vo;

import com.hf.friday.model.Chapter;
import com.hf.friday.model.Comic;
import lombok.Data;

/**
 * APP展示漫画所需要的数据
 * @Author CoolWind
 * @Date 2020/4/18 20:04
 */
@Data
public class ComicVO extends BaseVO{
    private Comic comic;
    private String host;
    private Chapter chapter;//最新章节
}
