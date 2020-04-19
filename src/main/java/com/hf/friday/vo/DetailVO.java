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
public class DetailVO {
    private List<Image> imageList;
    private Chapter chapter;
}
