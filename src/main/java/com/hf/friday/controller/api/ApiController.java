package com.hf.friday.controller.api;

import com.hf.friday.model.Chapter;
import com.hf.friday.model.Comic;
import com.hf.friday.model.SysFile;
import com.hf.friday.service.ComicService;
import com.hf.friday.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("${api-url}")
public class ApiController {
    @Autowired
    private ComicService comicService;

    @Autowired
    private FileService fileService;

    //跳转到页面
    @RequestMapping("/getPage")
    public ModelAndView getPage(ModelAndView modelAndView,String pageName)
    {
        modelAndView.setViewName(pageName);
        return modelAndView;
    }

    //跳转到页面
    @GetMapping("/goComicDetail/{param}")
    public String  goComicDetail(Model model,@PathVariable("param") int id)
    {
        Chapter chapter = new Chapter();
        chapter.setComicId(id);
        model.addAttribute(chapter);
        return "comic/comic-detail";
    }

}
