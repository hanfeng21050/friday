package com.hf.friday.service;

import com.alibaba.fastjson.JSONArray;
import com.hf.friday.base.Results;

public interface PermissionService {

    //返回菜单树
    Results<JSONArray> listAllPermission();
}
