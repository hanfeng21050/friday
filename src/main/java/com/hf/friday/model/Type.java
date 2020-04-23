package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class Type extends BaseEntity<Integer> {
    /**
     * 作品类型名称
     */
    private String name;

}