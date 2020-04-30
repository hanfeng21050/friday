package com.hf.friday.controller;

import com.hf.friday.vo.*;
import com.hf.friday.base.Results;
import com.hf.friday.util.StringUtil;
import com.hf.friday.model.*;
import com.hf.friday.service.ComicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
     * @param request 分页请求
     */
    @GetMapping("/list")
    @ResponseBody
    public Results<Comic> listByPage(PageTableRequest request)
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

    /**
     * 添加漫画需要用到的数据
     */
    @RequestMapping("/getTagAndType")
    @ResponseBody
    public Results<ComicDetailVO> getTagAndType()
    {
        return comicService.getTagAndType();
    }


    @ResponseBody
    @PostMapping("/add")
    public Results addComic(Comic comic,String tagList, Integer type)
    {
        log.info("ComicController.addComic() param:(Comic="+comic+";tagList="+tagList+";type=)"+type+"");
        return comicService.addComic(comic, StringUtil.String2Int2(tagList),type);
    }

    /**
     * 跳转到单图片上传页面
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
     * @param status 状态
     * @param id id
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
     */
    @ResponseBody
    @PostMapping("/app/imagelist")
    //@PreAuthorize("hasAnyAuthority('comic:query')")
    public Results<DetailVO> list(@RequestBody PageTableRequest request)
    {

        log.info("app:ComicController.list() param:(request = "+request+")");
        return comicService.list(request);
    }
    /**
     * 移动段返回章节列表
     */
    @ResponseBody
    @PostMapping("/app/getChapter")
    public Results<Chapter> getChapter(@RequestBody PageTableRequest request)
    {
        log.info("app:ComicController.list() param:(id = "+request.getId()+")");
        return comicService.getChapter(request.getId());
    }

    @PostMapping("/app/getComic")
    @ResponseBody
    public Results<ComicVO> getHotComic(@RequestBody PageTableRequest request)
    {
        request.countOffset();
        log.info("app:ComicController.getHotComic() param: (request=" + request +")");
        return comicService.getHotComic(request);
    }

    @ResponseBody
    @PostMapping("/app/getComicDetail")
    public Results<ComicDetailVO> getComicDetail(@RequestBody PageTableRequest request)
    {
        log.info("app:ComicController.getComicDetail() param:(id = "+request.getId()+")");
        return comicService.getComicDetail(request.getId());
    }

    @PostMapping("/app/login")
    @ResponseBody
    public Results<LoginVO> appLogin(@RequestBody SysUser user)
    {
        return comicService.login(user);
    }

    /**
     * 验证token的有效性
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/verify")
    public Results verify(@RequestBody PageTableRequest request)
    {
        return comicService.verify(request.getToken());
    }
}
