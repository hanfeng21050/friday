package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class ComicTag extends BaseEntity<Integer> {

    /**
     * 标签id
     */
    private Integer tagId;
    /**
     * 漫画id
     */
    private Integer comicId;
}