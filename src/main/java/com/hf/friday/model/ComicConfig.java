package com.hf.friday.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author 
 * 全局配置
 */
@Data
public class ComicConfig extends BaseEntity<Integer> {
    /**
     * 漫画总数
     */
    private Integer num;

    /**
     * 服务器主机
     */
    private String host;
    private static final long serialVersionUID = 1L;
}