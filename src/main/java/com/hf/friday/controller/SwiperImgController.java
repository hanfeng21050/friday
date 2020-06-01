package com.hf.friday.controller;

import com.hf.friday.base.Results;
import com.hf.friday.model.SwiperImg;
import com.hf.friday.model.SysUser;
import com.hf.friday.service.SwiperImgService;
import com.hf.friday.vo.ImageVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 轮播图管理
 * @Author CoolWind
 * @Date 2020/5/31 16:39
 */
@RequestMapping("/swiper")
@Controller
@Slf4j
public class SwiperImgController {
    @Autowired
    private SwiperImgService swiperImgService;

    /**
     * 跳转到预览页面
     * @param model
     * @return
     */
    @GetMapping("/preview")
    public String addUserPage(Model model)
    {
        return "comic/swiper-preview";
    }

    /**
     * 跳转到add页面
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String goadd(Model model)
    {
        model.addAttribute(new SwiperImg());
        return "comic/swiper-add";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Results select() {
        return swiperImgService.select();
    }

    @ResponseBody
    @PostMapping("/add")
    public Results insert(ImageVO vo) throws IOException {
        return swiperImgService.insert(vo);
    }
}
