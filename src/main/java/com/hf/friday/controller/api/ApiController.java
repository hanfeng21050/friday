package com.hf.friday.controller.api;

import com.hf.friday.model.SysFile;
import com.hf.friday.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("${api-url}")
public class ApiController {

    @Autowired
    private FileService fileService;

    //跳转到页面
    @RequestMapping("/getPage")
    public ModelAndView getPage(ModelAndView modelAndView,String pageName)
    {
        modelAndView.setViewName(pageName);
        return modelAndView;
    }

}
