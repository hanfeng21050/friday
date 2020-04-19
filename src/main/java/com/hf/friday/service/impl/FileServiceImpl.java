package com.hf.friday.service.impl;

import com.hf.friday.base.Results;
import com.hf.friday.dao.FileDao;
import com.hf.friday.dto.FileDto;
import com.hf.friday.model.SysFile;
import com.hf.friday.model.SysUser;
import com.hf.friday.service.FileService;
import com.hf.friday.service.UserService;
import com.hf.friday.util.ThumbnailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class FileServiceImpl implements FileService {
    @Value("${file.standardPath}")
    private String filePath;
    @Value(("${file.thumbnailPath}"))
    private String fileUploadStandardPath;//缩略图路径

    @Autowired
    private FileDao fileDao;
    @Autowired
    private final ResourceLoader resourceLoader;
    @Autowired
    private UserService userService;

    public FileServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public  Results upload(FileDto dto) throws IOException {
        MultipartFile file = dto.getFile();
        //文件名
        String originalFilename = file.getOriginalFilename();
        //文件大小
        DecimalFormat df = new DecimalFormat("0.0");//格式化，区小数后两位
        String size = df.format((double)file.getBytes().length/1024);


        //避免文件重名
        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String Uuid = UUID.randomUUID().toString();
        String uuidName = Uuid + extName;

        //文件上传
        FileCopyUtils.copy(file.getInputStream(),new FileOutputStream(new File(filePath+uuidName)));

        //缩略图上传
        ThumbnailUtil.storeThumbnail(filePath+uuidName,fileUploadStandardPath+uuidName);

        //文件分辨率 宽X长
        File outFIle = new File(filePath+uuidName);
        BufferedImage img = ImageIO.read(outFIle);
        String ratio = "" + img.getHeight() + "X" + img.getWidth();

        //上传者
        SysUser user = userService.getUserById(dto.getUserId().longValue());

        //文件信息保存到数据库
        SysFile sysFile = new SysFile();
        sysFile.setUserId(dto.getUserId());
        sysFile.setUuidName(uuidName);
        sysFile.setFileName(originalFilename);
        sysFile.setSize(size);
        sysFile.setUrl("/file/"+uuidName);
        sysFile.setThumbnailUrl("/file/thumbnail/"+uuidName);
        sysFile.setRatio(ratio);
        sysFile.setUploadUserId(dto.getUserId());
        sysFile.setUploadUserName(user.getUsername());

        fileDao.save(sysFile);

        return Results.success("上传成功",sysFile);
    }

    @Override
    public Results<SysFile> list(Integer offset, Integer limit,Integer userId) {
        return Results.success(fileDao.countFilesByUserId(userId),fileDao.getAllFilesByPageAndUserId(offset,limit,userId));
    }

    @Override
    public ResponseEntity download(Integer id) {
        try {
            //更新数据库信息
            fileDao.updateFileDownloadNum(id);

            SysFile sysFile = fileDao.findFileById(id);
            String uuidName = sysFile.getUuidName();
            // 获取本地文件系统中的文件资源
            FileSystemResource resource = new FileSystemResource(filePath + uuidName);
            // 解析文件的 mime 类型
            String mediaTypeStr = URLConnection.getFileNameMap().getContentTypeFor(uuidName);
            // 无法判断MIME类型时，作为流类型
            mediaTypeStr = (mediaTypeStr == null) ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mediaTypeStr;
            // 实例化MIME
            MediaType mediaType = MediaType.parseMediaType(mediaTypeStr);

            /*
             * 构造响应的头
             */
            HttpHeaders headers = new HttpHeaders();
            // 下载之后需要在请求头中放置文件名，该文件名按照ISO_8859_1编码。
            String filename = new String(sysFile.getFileName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            headers.setContentDispositionFormData("attachment", filename);
            headers.setContentType(mediaType);
            /*
             * 返还资源
             */
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.getInputStream().available())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SysFile findFileById(Integer id) {
        return fileDao.findFileById(id);
    }

    @Override
    public SysFile findFileByName(String fileName) {
        return fileDao.findFileByName(fileName);
    }

    @Override
    public int deleteFile(List<Integer> list) {
        SysFile sysFile = null;
        int count = 0;
        if(list.size() >0)
        {
            for (Integer id : list) {

                //通过id拿到文件实际保存的文件名
                sysFile = fileDao.findFileById(id);
                String uuidName = sysFile.getUuidName();

                //找到文件夹下的文件
                //判断文件是否存在
                File file = new File(Paths.get(filePath, uuidName).toString());
                File file1 = new File(Paths.get(fileUploadStandardPath,uuidName).toString());
                //删除文件
                if(file.exists() )
                {
                    file.delete();
                    count ++ ;
                }
                if(file1.exists())
                {
                    file1.delete();
                }


                //删除数据记录记录
                fileDao.deleteById(id);
            }
        }
        return count;
    }

    @Override
    public Results findFileByFuzzyFileName(Integer offset, Integer limit, String fileName,Integer userId) {
        return Results.success(fileDao.getFileCountByFuzzyFilename(fileName,userId),fileDao.findFileByFuzzyFileName(fileName,offset,limit,userId));
    }


}
