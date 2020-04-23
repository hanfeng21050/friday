package com.hf.friday.controller;

import com.hf.friday.base.PageTableRequest;
import com.hf.friday.base.Results;
import com.hf.friday.model.Comic;
import com.hf.friday.model.Tag;
import com.hf.friday.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author CoolWind
 * @Date 2020/4/23 13:15
 */
@Controller
@RequestMapping("/tag")
@Slf4j
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 分页查询
     * @param request
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Results<Tag> listByPage(PageTableRequest request)
    {
        request.countOffset();
        log.info("TagController.list() param:(request = "+request+")");
        return tagService.listByPage(request.getOffset(), request.getLimit());
    }


    /**
     * 切换状态
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/switchStat")
    @ResponseBody
    public Results switchStat(Boolean status,Integer id)
    {
        log.info("ComicController.getComicDetail() param:(id = "+id+")");
        return tagService.switchStat(status,id);
    }

}
