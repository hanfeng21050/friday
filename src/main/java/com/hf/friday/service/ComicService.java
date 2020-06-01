package com.hf.friday.service;

import com.hf.friday.model.SysUser;
import com.hf.friday.vo.*;
import com.hf.friday.base.Results;
import com.hf.friday.model.Chapter;
import com.hf.friday.model.Comic;

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

    Results<DetailVO> list(HtpRquest request);

    Results<Chapter> getChapter(int id);

    Results<ComicVO> getHotComic(HtpRquest request);

    Results<ComicDetailVO> getComicDetail(HtpRquest request);

    Results switchStat(Boolean status, Integer id);

    Results<ComicDetailVO> getTagAndType();

    Results<LoginVO> login(SysUser user);

    Results verify(String token);

    Results addComment(HtpRquest request);

    Results<CommentVO> getCommentList(HtpRquest request);

    Results collect(HtpRquest request);

    Results<CardVO> getCardList(HtpRquest request);

    Results<CardVO> getCardDetail(HtpRquest request);

    Results getUserInfo(HtpRquest request);

    Results getSwiperImg();

}
