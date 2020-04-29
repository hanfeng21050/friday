package com.hf.friday.controller;

import com.hf.friday.base.Results;
import com.hf.friday.vo.FormDataVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author CoolWind
 * @Date 2020/4/29 12:48
 */
@Controller
@RequestMapping("/card")
public class CardController {
    @ResponseBody
    @PostMapping("/add")
    public Results addCard(HttpServletRequest request, FormDataVo formData)
    {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> map = mulReq.getFileMap();

            // key为前端的name属性，value为上传的对象（MultipartFile）
            for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
                // 自己的保存文件逻辑
                System.out.println(entry.getValue());
            }
        }
        return null;
    }
}
