package com.hf.friday.controller;

import com.hf.friday.base.PageTableRequest;
import com.hf.friday.base.Results;
import com.hf.friday.model.Chapter;
import com.hf.friday.model.Comic;
import com.hf.friday.model.SysFile;
import com.hf.friday.service.ChapterService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Author CoolWind
 * @Date 2020/4/18 13:11
 */
@Controller
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    /**
     * 分页查询
     * @param request
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Results<Chapter> list(PageTableRequest request)
    {
        request.countOffset();
        return chapterService.list(request.getOffset(),request.getLimit(),request.getId());
    }

    /**
     * 跳转到上传页面
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/upload")
    public String goUploadPage(Model model,int id){
        Chapter chapter = new Chapter();
        chapter.setId(id);
        model.addAttribute(chapter);
        return "comic/comic-upload";
    }

    /**
     * 跳转到添加页面
     * @param model
     * @param comicId
     * @param uploadUser
     * @return
     */
    @GetMapping("/add")
    public String addPage(Model model, int comicId, String uploadUser){
        Comic comic = new Comic();
        comic.setId(comicId);
        comic.setUploadUser(uploadUser);
        model.addAttribute(comic);
        return "comic/chapter-add";
    }

    /**
     * 添加章节
     * @param chapter
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Results add(Chapter chapter){
        return chapterService.insert(chapter);
    }

    /**
     * 切换章节状态
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/switchStat")
    @ResponseBody
    public Results switchStat(Boolean status,Integer id)
    {
        return chapterService.switchStat(status,id);
    }
}
