package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class SwiperImg extends BaseEntity<Integer> {

    /**
     * 图片名称
     */
    private String name;

    /**
     * 链接
     */
    private String url;

    /**
     * 图片链接
     */
    private String src;

    /**
     * 描述
     */
    private String description;
}