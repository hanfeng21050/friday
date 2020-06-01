package com.hf.friday.vo;

import com.hf.friday.model.SysFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * APP
 * 由后台返回到前端图片详情页
 * 返回参数
 * 当前页数
 */
@Data
public class ImageVO extends BaseVO{
    private MultipartFile file;
    private Integer id;
    private String name;
    private String url;
    private String description;
}
