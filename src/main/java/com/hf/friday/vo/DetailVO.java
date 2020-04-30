package com.hf.friday.vo;

import com.hf.friday.model.Chapter;
import com.hf.friday.model.Image;
import lombok.Data;

import java.util.List;

/**
 * APP章节页面所需的信息
 * @Author CoolWind
 * @Date 2020/4/18 21:22
 */
@Data
public class DetailVO extends BaseVO{
    private List<Image> imageList;//图片列表
    private Chapter chapter;//当前章节
    private int count;//当前章节图片的总数
    private String host;
}
