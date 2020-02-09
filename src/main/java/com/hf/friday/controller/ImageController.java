package com.hf.friday.controller;

import com.hf.friday.base.Results;
import com.hf.friday.dto.ImageDto;
import com.hf.friday.model.SysFile;
import com.hf.friday.service.ImageService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;


    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/list/{page}/{limit}")
    @ResponseBody
    @ApiOperation(value = "分页获取图片信息", notes = "分页获取图片信息")//描述
    @PreAuthorize("hasAuthority('sys:image:query')")
    public Results list(@PathVariable Integer page,@PathVariable Integer limit)
    {
        Integer offset = 0;
        if(null == page || null == limit){
            offset = 0;
        }else {
            offset = (page - 1) * limit;
        }
        log.info("ImageController.list() param:(page = "+page+",limit="+limit+")");
        return imageService.list(offset, limit);
    }



    /**
     * 跳转到原图页面
     * @param model
     * @return
     */
    @GetMapping("/original_image/{id}")
    @PreAuthorize("hasAuthority('sys:image:query')")
    public String addUserPage(Model model,@PathVariable("id") Integer id)
    {
        ImageDto dto = new ImageDto();
        SysFile file = imageService.findImageById(id);
        //找到该id所在的页数
        Integer page = (imageService.findIndex(id)-1)/10 +1;
        dto.setFile(file);
        dto.setCurrentPage(page);
        System.out.println(page);
        model.addAttribute("dto",dto);
        return "image/img-show";
    }



    /**
     * 获取图片总数目
     * @return
     */
    @GetMapping("/countAll")
    @ResponseBody
    @ApiOperation(value = "获取图片总数目", notes = "获取图片总数目")//描述
    @PreAuthorize("hasAuthority('sys:image:query')")
    public Results countAll()
    {
        log.info("ImageController.countAll()");
        return imageService.countAll();
    }
}
