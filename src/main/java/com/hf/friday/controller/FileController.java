package com.hf.friday.controller;

import com.hf.friday.base.PageTableRequest;
import com.hf.friday.base.Results;
import com.hf.friday.dto.FileDto;
import com.hf.friday.model.SysFile;
import com.hf.friday.model.SysRole;
import com.hf.friday.model.SysUser;
import com.hf.friday.service.FileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/file")
@Slf4j
public class FileController {
    @Value("${file.path}")
    private String filePath;

    @Autowired
    private FileService fileService;

    @Autowired
    private ResourceLoader resourceLoader;


    /**
     * 分页查询
     * @param request
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:file:query')")
    @ApiOperation(value = "分页获取保存文件", notes = "用户分页获取文件")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "limit", required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "userId", required = true,dataType = "Integer"),
    })
    public Results<SysFile> list(PageTableRequest request)
    {
        request.countOffset();
        log.info("FileController.list() param:(request = "+request+")");
        return fileService.list(request.getOffset(), request.getLimit(),request.getUserId());
    }


    @GetMapping("/upload")
    @PreAuthorize("hasAuthority('sys:file:upload')")
    @ApiOperation(value = "用户上传文件界面", notes = "跳转到用户上传文件界面")//描述
    public String uploadPage(Model model)
    {
        model.addAttribute(new SysFile());
        return "file/file-upload";
    }


    @PostMapping("/upload")
    @ResponseBody
    @ApiOperation(value = "下载文件", notes = "下载用户文件")//描述
    @PreAuthorize("hasAuthority('sys:file:upload')")
    public Results upload(FileDto fileDto) throws IOException {
        log.info("FileController.upload() param:(fileDto = "+fileDto+")");
        return fileService.upload(fileDto);
    }


    @GetMapping("/download")
    @ApiOperation(value = "下载文件", notes = "下载用户文件")//描述
    @PreAuthorize("hasAuthority('sys:file:download')")
    public Results download(@RequestParam(value = "id") Integer id, HttpServletResponse res) throws IOException {
        log.info("FileController.download() param:(id = "+id+")");

        return fileService.download(res,id);
    }

    //格式转换 json格式的日期到Date类型
    String pattern = "yyyy-MM-dd";
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat(pattern),true));
    }
}
