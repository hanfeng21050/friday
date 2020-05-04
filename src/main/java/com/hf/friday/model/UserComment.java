package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 这张表记录了用户对评论的操作  
赞:1
踩:2   
未操作:0
 */
@Data
public class UserComment extends BaseEntity<Integer> {
    /**
     * 评论id
     */
    private Integer commentId;

    /**
     * 0 : 未操作  1:赞  2:踩
     */
    private Integer action;

    private static final long serialVersionUID = 1L;
}