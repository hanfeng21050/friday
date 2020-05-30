package com.hf.friday.service;

import com.hf.friday.base.Results;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Author CoolWind
 * @Date 2020/5/4 17:00
 */
public interface CardService {
    Results addCard(String text, Integer res, List<MultipartFile> list) throws IOException;
}
