package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
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

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private static final long serialVersionUID = 1L;
}