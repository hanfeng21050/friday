package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
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

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private static final long serialVersionUID = 1L;
}