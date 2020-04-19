package com.hf.friday.controller;

import com.hf.friday.base.PageTableRequest;
import com.hf.friday.base.Results;
import com.hf.friday.vo.ComicDetailVO;
import com.hf.friday.vo.ComicVO;
import com.hf.friday.vo.DetailVO;
import com.hf.friday.vo.ImageVO;
import com.hf.friday.model.*;
import com.hf.friday.service.ComicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/**
 * @Author CoolWind
 * @Date 2020/4/16 15:55
 */
@Controller
@RequestMapping("/comic")
@Slf4j
public class ComicController {
    @Autowired
    private ComicService comicService;

    /**
     * 分页查询
     * @param request
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Results<Comic> list(PageTableRequest request)
    {
        request.countOffset();
        log.info("ComicController.list() param:(request = "+request+")");
        return comicService.listByPage(request.getOffset(), request.getLimit());
    }


    @ResponseBody
    @GetMapping("/all")
    public Results findAll()
    {
        List<Comic> all = comicService.selectAll();
        log.info("ComicController.all()");
        return Results.success("success",all.size(),all);
    }

    @ResponseBody
    @PostMapping("/add")
    public Results addComic(@RequestParam HashMap<Object,Object> map)
    {
        System.out.println(map);
        return null;
        //log.info("ComicController.addComic() param:(Comic="+comic+")");
        //return comicService.insert(comic);
    }

    /**
     * 跳转到单图片上传页面
     * @param model
     * @return
     */
    @GetMapping("/uploadSingle")
    public String uploadPage(Model model,int id)
    {
        Comic comic = new Comic();
        comic.setId(id);
        model.addAttribute(comic);
        return "comic/comic-upload-single";
    }

    @PostMapping("/upload")
    @ResponseBody
    public Results upload(ImageVO dto) throws IOException {
        log.info("ComicController.upload() param:(ImageDto = "+dto+")");
        return comicService.upload(dto);
    }


    /**
     * 切换漫画状态
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/switchStat")
    @ResponseBody
    public Results switchStat(Boolean status,Integer id)
    {
        log.info("ComicController.getComicDetail() param:(id = "+id+")");
        return comicService.switchStat(status,id);
    }


    /**
     * 跳转到漫画添加
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String addComicPage(Model model)
    {
        model.addAttribute(new Comic());
        return "comic/comic-add";
    }


    /**
     * 返回一个章节页面的图片
     * 移动段返回接口
     * @param id 当前章节id
     * @return
     */
    @ResponseBody
    @GetMapping("/app/imagelist")
    //@PreAuthorize("hasAnyAuthority('comic:query')")
    public Results<DetailVO> list(int id)
    {
        log.info("app:ComicController.list() param:(id = "+id+")");
        return comicService.list(id);
    }


    /**
     * 移动段返回章节列表
     * @param id 当前漫画id
     */
    @ResponseBody
    @GetMapping("/app/getChapter")
    public Results<Chapter> getChapter(int id)
    {
        log.info("app:ComicController.list() param:(id = "+id+")");
        return comicService.getChapter(id);
    }

    @GetMapping("/app/getComic")
    @ResponseBody
    public Results<ComicVO> getHotComic(PageTableRequest request)
    {
        request.countOffset();
        log.info("app:ComicController.getHotComic() param: (request=" + request +")");
        return comicService.getHotComic(request);
    }

    @ResponseBody
    @RequestMapping("/app/getComicDetail")
    public Results<ComicDetailVO> getComicDetail(Integer id)
    {
        log.info("app:ComicController.getComicDetail() param:(id = "+id+")");
        return comicService.getComicDetail(id);
    }


}
