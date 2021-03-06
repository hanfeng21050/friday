package com.hf.friday.model;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysFile extends BaseEntity {
    private Integer id;
    private String fileName; //原始的文件名
    private String uuidName; //保存在本地的文件名
    private Integer downloadNum;//下载次数
    private Integer userId; //文件所属用户
    private String size; //字节大小
    private String url; //原图地址
    private String ratio;//分辨率
    private Integer uploadUserId;//上传者id
    private String uploadUserName;//上传者姓名
    private String thumbnailUrl; //缩略图地址
    private String description;//描述
}
