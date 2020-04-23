package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class ComicConfig extends BaseEntity<Integer> {
    /**
     * 漫画总数
     */
    private Integer num;
}