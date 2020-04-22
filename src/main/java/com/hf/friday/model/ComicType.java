package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class ComicType implements Serializable {
    /**
     * 唯一
     */
    private Integer id;

    /**
     * 漫画id
     */
    private Integer comicId;

    /**
     * 类型id
     */
    private Integer typeId;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private static final long serialVersionUID = 1L;
}