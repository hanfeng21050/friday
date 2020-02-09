package com.hf.friday.dto;

import com.hf.friday.model.SysFile;
import lombok.Data;

/**
 * 由后台返回到前端图片详情页
 * 返回参数
 * 当前页数
 */
@Data
public class ImageDto {
    private SysFile file;
    private Integer currentPage;
}
