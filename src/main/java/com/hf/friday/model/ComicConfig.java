package com.hf.friday.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class ComicConfig implements Serializable {
    private Integer id;

    /**
     * 漫画总数
     */
    private Integer num;

    private static final long serialVersionUID = 1L;
}