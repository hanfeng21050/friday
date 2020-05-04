package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author 
 * 漫画章节
 */
@Data
public class Chapter extends BaseEntity<Integer> {

    /**
     * 章节名称
     */
    private String title;

    /**
     * 漫画id
     */
    private Integer comicId;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 上传者
     */
    private String uploadUser;

    /**
     * 包含的图片数量
     */
    private Integer num;

    /**
     * 表示第几话
     */
    private Integer index;

}