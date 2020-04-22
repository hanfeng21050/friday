package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class Image implements Serializable {
    /**
     * 唯一
     */
    private Integer id;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 图片信息
     */
    private String info;

    /**
     * 所属章节
     */
    private Integer chapterId;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private static final long serialVersionUID = 1L;
}