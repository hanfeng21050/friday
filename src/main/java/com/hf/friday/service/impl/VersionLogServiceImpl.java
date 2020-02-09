package com.hf.friday.service.impl;

import com.hf.friday.base.Results;
import com.hf.friday.dao.VersionLogDao;
import com.hf.friday.service.VersionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class VersionLogServiceImpl implements VersionLogService {
    @Autowired
    private VersionLogDao versionLogDao;

    @Override
    public Results listBySizeAndNew(Integer size) {
        return Results.success(0,versionLogDao.listBySizeAndNew(size));
    }
}
