package com.hf.friday.service;

import com.hf.friday.base.PageTableRequest;
import com.hf.friday.base.Results;
import com.hf.friday.model.Chapter;
import com.hf.friday.model.Comic;
import com.hf.friday.vo.ComicDetailVO;
import com.hf.friday.vo.ComicVO;
import com.hf.friday.vo.DetailVO;
import com.hf.friday.vo.ImageVO;

import java.io.IOException;
import java.util.List;

/**
 * @Author CoolWind
 * @Date 2020/4/16 16:48
 */
public interface ComicService {

    Results<Comic> listByPage(Integer offset, Integer limit);

    public List<Comic> selectAll();

    public Comic selectById(int id);

    public Results insert(Comic comic);

    public Results upload(ImageVO imageVO) throws IOException;

    Results<DetailVO> list(int id);

    Results<Chapter> getChapter(int id);

    Results<ComicVO> getHotComic(PageTableRequest request);

    Results<ComicDetailVO> getComicDetail(Integer id);

    Results switchStat(Boolean status, Integer id);
}
