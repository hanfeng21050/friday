package com.hf.friday.service;

import com.hf.friday.base.Results;
import com.hf.friday.vo.HtpRquest;
import com.hf.friday.vo.ImageVO;

import java.io.IOException;

/**
 * @Author CoolWind
 * @Date 2020/4/18 14:27
 */
public interface ImageService {
    Results upload(ImageVO dto) throws IOException;

    Results selectAllByChapterId(Integer id);
}
