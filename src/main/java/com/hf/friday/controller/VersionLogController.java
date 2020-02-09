package com.hf.friday.controller;

import com.hf.friday.base.Results;
import com.hf.friday.service.VersionLogService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/versionlog")
@Slf4j
public class VersionLogController {
    @Autowired
    private VersionLogService versionLogService;



    /**
     * 分页查询
     * @param size 查询条数
     * @return
     */
    @GetMapping("/listBySizeAndNew")
    @ResponseBody
    @ApiOperation(value = "查询最近的size条日志记录", notes = "查询最近的size条日志记录")//描述
    @ApiImplicitParam(name = "size", value = "查询条数", required=true)
    @PreAuthorize("hasAuthority('sys:log:query')")
    public Results listBySizeAndNew(Integer size)
    {
        log.info("UserController.listBySizeAndNew() param:(size = "+size+")");
        return versionLogService.listBySizeAndNew(size);
    }
}
