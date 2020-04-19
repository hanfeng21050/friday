package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 漫画章节
 */
@Data
public class Chapter implements Serializable {
    /**
     * 唯一
     */
    private Integer id;

    /**
     * 章节名称
     */
    private String title;

    /**
     * 漫画id
     */
    private Integer comicId;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 上传者
     */
    private String uploadUser;

    /**
     * 状态  刚上传时为0  表示未审核
     */
    private Integer status;

    /**
     * 包含的图片数量
     */
    private Integer num;

    /**
     * 表示第几话
     */
    private Integer index;

    private static final long serialVersionUID = 1L;
}