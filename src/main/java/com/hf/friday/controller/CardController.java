package com.hf.friday.controller;

import com.hf.friday.base.ResponseCode;
import com.hf.friday.base.Results;
import com.hf.friday.service.CardService;
import com.hf.friday.util.TokenUtil;
import com.hf.friday.vo.FormDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author CoolWind
 * @Date 2020/4/29 12:48
 */
@Controller
@RequestMapping("/card")
@Slf4j
public class CardController {
    @Autowired
    private CardService cardService;

    @ResponseBody
    @PostMapping("/add")
    public Results addCard(HttpServletRequest request, FormDataVo formData) throws IOException {
        log.info("app:CardController.add()  userId="+formData.getUserId());
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> map = mulReq.getFileMap();
            List<MultipartFile> list = new ArrayList<>();
            // key为前端的name属性，value为上传的对象（MultipartFile）
            for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
                list.add(entry.getValue());
            }
            return cardService.addCard(formData.getText(),formData.getUserId(),list);
        }
        return Results.failure();
    }
}
