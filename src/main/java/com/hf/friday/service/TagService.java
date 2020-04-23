package com.hf.friday.service;

import com.hf.friday.base.Results;
import com.hf.friday.model.Tag;

/**
 * @Author CoolWind
 * @Date 2020/4/23 13:17
 */
public interface TagService {
    Results<Tag> listByPage(Integer offset, Integer limit);

    Results switchStat(Boolean status, Integer id);
}
