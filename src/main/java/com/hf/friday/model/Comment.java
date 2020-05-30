package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 评论
 */
@Data
public class Comment  extends BaseEntity<Integer> {

    /**
     * 发表评论的用户
     */
    private Integer userId;

    /**
     * 被评论的用户id
     */
    private Integer user2Id;

    /**
     * 评论目标id,可以是漫画id,也可以是社区帖子id
     */
    private Integer targetId;

    /**
     * 1 评论漫画  2评论帖子   3评论其他人的评论
     */
    private Integer type;

    /**
     * 评论
     */
    private String text;

    /**
     * 点赞数
     */
    private Integer likeNum;

    /**
     * 不喜欢数
     */
    private Integer dislikeNum;
}