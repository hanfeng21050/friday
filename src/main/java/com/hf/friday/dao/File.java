package com.hf.friday.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class File {
    private Integer id;
    private String port_level;
    private String is_directory;
    private Integer parent_id;
    private String description;
    private String folder_name;
    private String upd_username;
    private String crt_username;
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private Timestamp upd_date;
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    private Timestamp crt_date;
    private String url;
}
