package com.hf.friday.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 发布帖子时前端传过来的信息
 * @Author CoolWind
 * @Date 2020/4/29 13:17
 */
@Data
public class FormDataVo extends BaseVO{
    private String text;//帖子内容
    private Integer userId;
    
}
