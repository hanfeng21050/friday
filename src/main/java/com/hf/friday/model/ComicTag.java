package com.hf.friday.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class ComicTag implements Serializable {
    /**
     * 唯一
     */
    private Integer id;

    /**
     * 标签id
     */
    private Integer tagId;

    /**
     * 漫画id
     */
    private Integer comicId;

    private static final long serialVersionUID = 1L;
}