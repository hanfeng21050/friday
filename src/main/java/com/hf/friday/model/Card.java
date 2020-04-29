package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 社区帖子
 */
@Data
public class Card extends BaseEntity<Integer>{
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 正文
     */
    private String text;

    /**
     * 类型,0 无图 1有图
     */
    private Integer type;

    /**
     * 查看次数
     */
    private Integer viewNum;

    /**
     * 赞数量
     */
    private Integer likeNum;

    /**
     * 踩数量
     */
    private Integer dislikeNum;
}