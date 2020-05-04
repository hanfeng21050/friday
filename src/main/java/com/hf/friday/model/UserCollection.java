package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author 
 * 记录 用户收藏的漫画
 */
@Data
public class UserCollection extends BaseEntity<Integer> {
    /**
     * 收藏id
     */
    private Integer targetId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 目标类型 0:收藏漫画  1:关注用户
     */
    private Integer type;
}