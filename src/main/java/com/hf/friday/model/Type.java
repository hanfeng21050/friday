package com.hf.friday.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class Type implements Serializable {
    private Integer id;

    /**
     * 作品类型名称
     */
    private String name;

    private static final long serialVersionUID = 1L;
}