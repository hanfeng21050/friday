package com.hf.friday.service;

import com.hf.friday.base.Results;
import com.hf.friday.model.SwiperImg;
import com.hf.friday.vo.ImageVO;

import java.util.List;

/**
 * @Author CoolWind
 * @Date 2020/5/31 16:49
 */
public interface SwiperImgService {
    Results select();

    Results insert(ImageVO vo);
}
