package com.hf.friday.service;

import com.hf.friday.base.Results;
import com.hf.friday.dto.FileDto;
import com.hf.friday.model.SysFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileService {

    //文件上传
    Results upload(FileDto fileDto) throws IOException;

    //分页查询
    Results<SysFile> list(Integer offset, Integer limit,Integer userId);

    //通过文件id下载文件
    Results download(HttpServletResponse response, Integer id);

    //通过文件id查询文件
    SysFile findFileById(Integer id);
}
