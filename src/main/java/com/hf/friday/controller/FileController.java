package com.hf.friday.controller;

import com.hf.friday.vo.HtpRquest;
import com.hf.friday.base.Results;
import com.hf.friday.dto.FileDto;
import com.hf.friday.model.SysFile;
import com.hf.friday.service.FileService;
import com.hf.friday.util.StringUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/file")
@Slf4j
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 分页查询
     * @param request
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    //@PreAuthorize("hasAuthority('sys:file:query')")
    @ApiOperation(value = "分页获取保存文件", notes = "用户分页获取文件")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "limit", required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "userId", required = true,dataType = "Integer"),
    })
    public Results<SysFile> list(HtpRquest request)
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
    @ApiOperation(value = "上传文件", notes = "上传图片文件")//描述
    @PreAuthorize("hasAuthority('sys:file:upload')")
    public Results upload(FileDto fileDto) throws IOException {
        log.info("FileController.upload() param:(fileDto = "+fileDto+")");
        return fileService.upload(fileDto);
    }


    @GetMapping("/download")
    @ApiOperation(value = "下载文件", notes = "下载用户文件")//描述
    @PreAuthorize("hasAuthority('sys:file:download')")
    public ResponseEntity download(@RequestParam(value = "id") Integer id) throws IOException {
        log.info("FileController.download() param:(id = "+id+")");
        return fileService.download(id);
    }

    @GetMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:file:del')")
    @ApiOperation(value = "删除文件", notes = "删除文件 exp:1,2,3,")//描述
    @ApiImplicitParam(name = "ids", value = "文件id集合", required = true, dataType = "String")
    public Results deleteFile(String ids)
    {
        log.info("FileController.deleteFile() param:(ids = "+ids+")");
        List<Integer> list = StringUtil.String2Int(ids);
        int count = fileService.deleteFile(list);
        if(count > 0)
        {
            return Results.success();
        }else
        {
            return Results.failure();
        }
    }

    @GetMapping("/findFileByFuzzyFileName")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:file:query')")
    @ApiOperation(value = "模糊查询文件信息", notes = "模糊搜索查询文件信息")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName",value = "模糊搜索的文件名", required = true),
            @ApiImplicitParam(name = "userId",value = "用户id", required = true),
    })
    public Results findFileByFuzzyFileName(HtpRquest request, String fileName, Integer userId)
    {
        log.info("FileController.findFileByFuzzyFileName() param:(request = "+request+" fileName:"+fileName+")");
        request.countOffset();
        return fileService.findFileByFuzzyFileName(request.getOffset(),request.getLimit(),fileName,userId);
    }


    //格式转换 json格式的日期到Date类型
    String pattern = "yyyy-MM-dd HH:mm:ss";
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat(pattern),true));
    }
}
