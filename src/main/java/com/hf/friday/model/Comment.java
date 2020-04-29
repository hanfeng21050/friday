package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 评论
 */
@Data
public class Comment extends BaseEntity<Integer> {
    /**
     * 用户
     */
    private Integer userId;

    /**
     * 评论目标id,可以是漫画id,也可以是社区帖子id
     */
    private Integer targetId;

    /**
     * 点赞数
     */
    private Integer likeMun;

    /**
     * 不喜欢数
     */
    private Integer dislekeNum;
    /**
     * 评论
     */
    private String text;
}