package com.hf.friday.service;

import com.hf.friday.base.Results;

public interface VersionLogService {

    /**
     * 查询最新的size条记录
     * @param size
     * @return
     */
    Results listBySizeAndNew(Integer size);
}
