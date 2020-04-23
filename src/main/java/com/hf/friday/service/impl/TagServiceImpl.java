package com.hf.friday.service.impl;

import com.hf.friday.base.Constants;
import com.hf.friday.base.Results;
import com.hf.friday.dao.TagDAO;
import com.hf.friday.model.*;
import com.hf.friday.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author CoolWind
 * @Date 2020/4/23 13:17
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDAO tagDAO;

    @Override
    public Results<Tag> listByPage(Integer offset, Integer limit) {
        TagExample tagExample = new TagExample();
        tagExample.setOffset((long)offset);
        tagExample.setLimit(limit);
        List<Tag> tagList = tagDAO.selectByExample(tagExample);

        tagExample.clear();
        int count = (int) tagDAO.countByExample(tagExample);

        return Results.success("success",count,tagList);
    }

    @Override
    public Results switchStat(Boolean status, Integer id) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setStatus(status ? 1 : 0);
        return tagDAO.updateByPrimaryKeySelective(tag) == 1 ? Results.success():Results.failure();
    }
}
