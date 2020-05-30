package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 图片
 */
@Data
public class Image extends BaseEntity<Integer> {

    /**
     * 图片地址
     */
    private String url;

    /**
     * 图片信息
     */
    private String info;

    /**
     * 目标id
     */
    private Integer targetId;

    /**
     * 类型  0  表示漫画图片.   1表示社区发布图片
     */
    private Integer type;

}