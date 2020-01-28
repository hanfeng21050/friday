package com.hf.friday.service;

import com.hf.friday.base.Results;
import com.hf.friday.dto.FileDto;
import com.hf.friday.model.SysFile;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface FileService {

    //文件上传
    Results upload(FileDto fileDto) throws IOException;

    //分页查询
    Results<SysFile> list(Integer offset, Integer limit,Integer userId);

    //通过文件id下载文件
    ResponseEntity download(Integer id);

    //通过文件id查询文件
    SysFile findFileById(Integer id);

    //通过文件名查询文件
    SysFile findFileByName(String fileName);

    //删除文件
    int deleteFile(List<Integer> list);

    //模糊查询
    Results findFileByFuzzyFileName(Integer offset, Integer limit, String fileName,Integer userId);

}
