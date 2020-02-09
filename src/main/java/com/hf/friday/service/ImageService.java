package com.hf.friday.service;

import com.hf.friday.base.Results;
import com.hf.friday.model.SysFile;

import javax.xml.transform.Result;

public interface ImageService {

    Results list(Integer offset, Integer limit);

    Results countAll();

    SysFile findImageById(Integer id);

    Integer findIndex(Integer id);
}
