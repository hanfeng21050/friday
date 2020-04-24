package com.hf.friday.service;

import com.hf.friday.base.Results;
import com.hf.friday.model.Tag;
import com.hf.friday.vo.ImageVO;

import java.io.IOException;

/**
 * @Author CoolWind
 * @Date 2020/4/23 13:17
 */
public interface TagService {
    Results<Tag> listByPage(Integer offset, Integer limit);

    Results switchStat(Boolean status, Integer id);

    Results addComic(ImageVO vo) throws IOException;
}
