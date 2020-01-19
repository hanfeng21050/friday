package com.hf.friday.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DruidStatController {
    @RequestMapping("/druid/stat")
    public Object druidStat()
    {
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}
