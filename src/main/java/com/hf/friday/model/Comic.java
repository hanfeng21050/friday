package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class Comic implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 漫画编号
     */
    private String no;

    /**
     * 漫画标题
     */
    private String title;

    /**
     * 封面
     */
    private String coverImg;

    /**
     * 作品公告
     */
    private String announcement;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss")

    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private Date updateTime;

    /**
     * 上传者
     */
    private String uploadUser;

    /**
     * 作者
     */
    private String author;

    /**
     * 状态 0上传未审 1 审核通过
     */
    private Integer status;

    /**
     * 收藏数量
     */
    private Integer likeNum;

    /**
     * 热度
     */
    private Integer fire;

    /**
     * 所包含的图片数量
     */
    private Integer count;

    /**
     * 作品介绍
     */
    private String info;

    /**
     * 评分
     */
    private Double score;

    private static final long serialVersionUID = 1L;
}