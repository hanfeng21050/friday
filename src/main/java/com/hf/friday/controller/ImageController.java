package com.hf.friday.controller;

import com.hf.friday.base.Results;
import com.hf.friday.vo.HtpRquest;
import com.hf.friday.vo.ImageVO;
import com.hf.friday.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Author CoolWind
 * @Date 2020/4/18 14:25
 */
@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    @ResponseBody
    public Results upload(ImageVO dto) throws IOException {
       return   imageService.upload(dto);
    }


    @GetMapping("/list/{id}")
    @ResponseBody
    public Results selectAllByChapterId(@PathVariable("id") Integer id) throws IOException {
        return  imageService.selectAllByChapterId(id);
    }
}
