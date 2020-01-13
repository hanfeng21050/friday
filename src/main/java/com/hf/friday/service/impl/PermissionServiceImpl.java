package com.hf.friday.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.hf.friday.base.Results;
import com.hf.friday.dao.PermissionDao;
import com.hf.friday.model.SysPermission;
import com.hf.friday.service.PermissionService;
import com.hf.friday.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Results<JSONArray> listAllPermission() {
        List<SysPermission> datas = permissionDao.findAll();
        JSONArray array = new JSONArray();
        TreeUtil.setPermissionsTree(0,datas,array);
        return Results.success(array);
    }
}
