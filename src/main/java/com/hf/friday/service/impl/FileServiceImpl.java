package com.hf.friday.service.impl;

import com.hf.friday.base.Results;
import com.hf.friday.dao.FileDao;
import com.hf.friday.dto.FileDto;
import com.hf.friday.model.SysFile;
import com.hf.friday.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class FileServiceImpl implements FileService {
    @Value("${file.path}")
    private String filePath;

    @Autowired
    private FileDao fileDao;
    @Autowired
    private final ResourceLoader resourceLoader;

    public FileServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Results upload(FileDto fileDto) throws IOException {
        MultipartFile file = fileDto.getFile();
        //文件名
        String originalFilename = file.getOriginalFilename();
        //避免文件重名
        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String uuidName = UUID.randomUUID().toString() + extName;
        //文件上传
        FileCopyUtils.copy(file.getInputStream(),new FileOutputStream(new File(filePath+uuidName)));

        //文件信息保存到数据库
        SysFile sysFile = new SysFile();
        sysFile.setUserId(fileDto.getUserId());
        sysFile.setUuidName(uuidName);
        sysFile.setFileName(originalFilename);
        sysFile.setUrl("/file/"+originalFilename);
        fileDao.save(sysFile);

        return Results.success();
    }

    @Override
    public Results<SysFile> list(Integer offset, Integer limit,Integer userId) {
        return Results.success(fileDao.countFilesByUserId(userId),fileDao.getAllFilesByPageAndUserId(offset,limit,userId));
    }

    @Override
    public Results download(HttpServletResponse res, Integer id) {
        SysFile sysFile = fileDao.findFileById(id);

        //设置响应头
        res.setContentType("application/force-download");// 设置强制下载不打开
        try {
            res.addHeader("Content-Disposition", "attachment;fileName=" +
                    URLEncoder.encode(sysFile.getFileName(), "utf-8"));// 设置文件名
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        res.setHeader("Context-Type", "application/xmsdownload");


        //判断文件是否存在
        File file = new File(Paths.get(filePath, sysFile.getUuidName()).toString());
        if (file.exists()) {
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = res.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                return Results.success();
            } catch (Exception e) {
                e.printStackTrace();
                return Results.failure();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public SysFile findFileById(Integer id) {
        return fileDao.findFileById(id);
    }
}
