package com.hf.friday.service.impl;

import com.hf.friday.base.Results;
import com.hf.friday.dao.ImageDao;
import com.hf.friday.model.SysFile;
import com.hf.friday.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageDao imageDao;

    @Override
    public Results<SysFile> list(Integer offset, Integer limit) {
        return Results.success(imageDao.findImageCountAll(),imageDao.findImageAll(offset,limit));
    }

    @Override
    public Results countAll() {
        return Results.success(imageDao.findImageCountAll(),null);
    }

    @Override
    public SysFile findImageById(Integer id) {
        if(id != null)
        {
            return imageDao.findImageById(id);
        }
        return null;
    }

    @Override
    public Integer findIndex(Integer id) {
        return imageDao.findIndex(id);
    }
}
