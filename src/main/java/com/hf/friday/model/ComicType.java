package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class ComicType extends BaseEntity<Integer> {
    /**
     * 漫画id
     */
    private Integer comicId;

    /**
     * 类型id
     */
    private Integer typeId;
}