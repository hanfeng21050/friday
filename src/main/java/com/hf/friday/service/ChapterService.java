package com.hf.friday.service;

import com.hf.friday.base.Results;
import com.hf.friday.model.Chapter;
import com.hf.friday.model.SysFile;

import java.util.List;

/**
 * @Author CoolWind
 * @Date 2020/4/18 13:26
 */
public interface ChapterService {
    Results<Chapter> list(Integer offset, Integer limit, Integer id);

    Results insert(Chapter chapter);

    Results switchStat(Boolean status, Integer id);

    Results deleteList(List<Integer> ids);

    Results update(Chapter chapter);
}
