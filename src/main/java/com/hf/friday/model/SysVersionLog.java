package com.hf.friday.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class SysVersionLog implements Serializable {
    private Integer id;
    private String version;
    private String logDetails;
    private String createUsername;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    private Integer createUserId;
}
