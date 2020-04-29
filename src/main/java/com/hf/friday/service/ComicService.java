package com.hf.friday.service;

import com.hf.friday.vo.PageTableRequest;
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

    List<Comic> selectAll();

    Comic selectById(int id);

    Results insert(Comic comic);

    Results addComic(Comic comic, List<Integer> string2Int, Integer type);

    Results upload(ImageVO imageVO) throws IOException;

    Results<DetailVO> list(PageTableRequest request);

    Results<Chapter> getChapter(int id);

    Results<ComicVO> getHotComic(PageTableRequest request);

    Results<ComicDetailVO> getComicDetail(Integer id);

    Results switchStat(Boolean status, Integer id);

    Results<ComicDetailVO> getTagAndType();
}
