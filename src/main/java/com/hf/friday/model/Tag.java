package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 标签
 */
@Data
public class Tag extends BaseEntity<Integer> {
    /**
     * 标签
     */
    private String name;

    /**
     * 标签展示图
     */
    private String src;


}