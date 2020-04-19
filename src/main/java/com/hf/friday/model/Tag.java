package com.hf.friday.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class Tag implements Serializable {
    /**
     * 唯一
     */
    private Integer id;

    /**
     * 标签
     */
    private String name;

    private static final long serialVersionUID = 1L;
}