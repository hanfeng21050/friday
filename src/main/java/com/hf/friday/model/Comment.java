package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class Comment extends BaseEntity<Integer> {
    /**
     * 用户
     */
    private Integer userId;

    /**
     * 漫画id
     */
    private Integer comicId;

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