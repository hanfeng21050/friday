package com.hf.friday.service.impl;

import com.hf.friday.base.Constants;
import com.hf.friday.base.PageTableRequest;
import com.hf.friday.base.Results;
import com.hf.friday.dao.*;
import com.hf.friday.model.*;
import com.hf.friday.service.ComicService;
import com.hf.friday.util.StringUtil;
import com.hf.friday.vo.ComicDetailVO;
import com.hf.friday.vo.ComicVO;
import com.hf.friday.vo.DetailVO;
import com.hf.friday.vo.ImageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author CoolWind
 * @Date 2020/4/16 16:48
 */
@Service
@Transactional
@Slf4j
public class ComicServiceImpl implements ComicService {

    @Value("${comic.path}")
    private String comicPath;
    @Value("${comic.host}")
    private String host;

    @Autowired
    private ComicDAO comicDAO;
    @Autowired
    private ImageDAO imageDAO;
    @Autowired
    private ComicConfigDAO comicConfigDAO;
    @Autowired
    private ChapterDAO chapterDAO;
    @Autowired
    private TagDAO tagDAO;
    @Autowired
    private TypeDAO typeDAO;
    @Autowired
    private ComicTagDAO comicTagDAO;
    @Autowired
    private ComicTypeDAO comicTypeDAO;

    @Override
    public Results<Comic> listByPage(Integer offset, Integer limit) {
        ComicExample example = new ComicExample();
        example.setLimit(limit);
        example.setOffset((long)offset);
        List<Comic> comicList = comicDAO.selectByExample(example);
        int count = (int) comicDAO.countByExample(new ComicExample());
        return Results.success(count,comicList);
    }

    @Autowired



    @Override
    public List<Comic> selectAll()
    {
        return comicDAO.selectByExample(new ComicExample());
    }

    @Override
    public Comic selectById(int id) {
        return comicDAO.selectByPrimaryKey(id);
    }

    @Override
    public Results insert(Comic comic) {
        //生成编号
        ComicConfig comicConfig = comicConfigDAO.selectByPrimaryKey(Constants.CONFIGID);
        comicConfig.setNum(comicConfig.getNum() + 1);
        comicConfigDAO.updateByPrimaryKey(comicConfig);

        String no = StringUtil.genNO(comicConfig.getNum());
        comic.setNo(no);
        comic.setStatus(0);
        comic.setCreateTime(new Date());
        comic.setLikeNum(0);
        comic.setCount(0);
        comic.setFire(0);

        return comicDAO.insertSelective(comic) > 0 ? Results.success() : Results.failure();
    }

    @Override
    public Results addComic(Comic comic, List<Integer> tagIdList, Integer typeId) {
        //保存comic
        ComicConfig comicConfig = comicConfigDAO.selectByPrimaryKey(Constants.CONFIGID);
        comicConfig.setNum(comicConfig.getNum() + 1);
        comicConfigDAO.updateByPrimaryKey(comicConfig);
        String no = StringUtil.genNO(comicConfig.getNum());
        comic.setNo(no);
        comic.setStatus(0);
        comic.setCreateTime(new Date());
        comic.setLikeNum(0);
        comic.setCount(0);
        comic.setFire(0);
        comicDAO.insertSelective(comic);

        int comicId = comic.getId();
        //保存type
        ComicType comicType = new ComicType();
        comicType.setComicId(comicId);
        comicType.setTypeId(typeId);
        comicType.setCreateTime(new Date());
        comicTypeDAO.insertSelective(comicType);

        //保存标签
        for (Integer tagId : tagIdList) {
            ComicTag comicTag = new ComicTag();
            comicTag.setComicId(comicId);
            comicTag.setTagId(tagId);
            comicTag.setCreateTime(new Date());
            comicTagDAO.insertSelective(comicTag);
        }
        return Results.success();
    }

    @Override
    public Results upload(ImageVO imageVO) throws IOException {
        Comic comic = comicDAO.selectByPrimaryKey(imageVO.getId());
        //漫画编号
        String no = comic.getNo();
        //封面名
        String imgName = no + "0000";

        MultipartFile img = imageVO.getFile();
        String extName = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
        String url = comicPath+no+"/"+imgName+extName;

        File file = new File(url);
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }

        FileCopyUtils.copy(img.getInputStream(),new FileOutputStream(file));
        comic.setCoverImg("/comic/" + no+"/"+imgName+extName);
        int x = comicDAO.updateByPrimaryKey(comic);

        return x == 1 ? Results.success() : Results.failure();
    }


    /**
     * 获取全部章节
     * @param id
     * @return
     */
    @Override
    public Results<Chapter> getChapter(int id) {
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.setOrderByClause("chapter.index ASC");
        chapterExample.createCriteria().andComicIdEqualTo(id).andStatusEqualTo(1);
        //List<Chapter> chapters = chapterDAO.listByComicId(id);
        List<Chapter> chapters = chapterDAO.selectByExample(chapterExample);
        return Results.success("success",chapters.size(),chapters);
    }


    /**
     * 返回一个章节页面的图片 分页
     * @return
     */
    @Override
    public Results<DetailVO> list(PageTableRequest request) {

        //返回图片
        request.countOffset();
        List<Image> images = imageDAO.listByChapterId(request.getId(),request.getOffset(),request.getLimit());
        //返回当前章节图片的总数
        ImageExample example = new ImageExample();
        example.createCriteria().andChapterIdEqualTo(request.getId());
        long count = imageDAO.countByExample(example);
        ComicConfig comicConfig = comicConfigDAO.selectByPrimaryKey(Constants.CONFIGID);

        Chapter chapter = chapterDAO.selectByPrimaryKey(request.getId());
        DetailVO detailVO = new DetailVO();
        detailVO.setChapter(chapter);
        detailVO.setImageList(images);
        detailVO.setCount((int) count);
        detailVO.setHost(comicConfig.getHost());
        return Results.success("success",detailVO);
    }


    /**
     * app index页面所需数据
     * @return
     */
    @Override
    public Results<ComicVO> getHotComic(PageTableRequest request) {
        if(request.getType() == 1)
        {
            ComicConfig comicConfig = comicConfigDAO.selectByPrimaryKey(Constants.CONFIGID);
            ComicExample comicExample = new ComicExample();
            comicExample.setOffset((long) request.getOffset());
            comicExample.setLimit(request.getLimit());
            comicExample.createCriteria().andStatusEqualTo(1);
            comicExample.setOrderByClause("comic.fire desc");
            List<Comic> comicList = comicDAO.selectByExample(comicExample);

            List<ComicVO> list = new ArrayList<>();
            for (Comic comic1 : comicList) {
                ComicVO dto = new ComicVO();
                //最新章节
                Chapter chapter = chapterDAO.selectNew(comic1.getId());
                dto.setChapter(chapter);
                dto.setComic(comic1);
                dto.setHost(comicConfig.getHost());
                list.add(dto);
            }
            return Results.success("success",list.size(),list);
        }
        return Results.failure();
    }

    /**
     * app ComicDetail页面所需数据
     * @param id
     * @return
     */
    @Override
    public Results<ComicDetailVO> getComicDetail(Integer id) {
        ComicConfig comicConfig = comicConfigDAO.selectByPrimaryKey(Constants.CONFIGID);
        //查询漫画
        Comic comic = comicDAO.selectByPrimaryKey(id);
        //推荐漫画
        ComicExample comicExample = new ComicExample();
        comicExample.setOffset(0L);
        comicExample.setLimit(10);
        comicExample.createCriteria().andStatusEqualTo(1);
        comicExample.setOrderByClause("comic.fire desc");
        List<Comic> comicList = comicDAO.selectByExample(comicExample);
        //List<Comic> comicList = comicDAO.selectHost(0, 10);

        //查询章节
        ChapterExample example = new ChapterExample();
        example.setOrderByClause("chapter.index ASC");
        example.createCriteria().andComicIdEqualTo(comic.getId()).andStatusEqualTo(1);
        List<Chapter> chapterList = chapterDAO.selectByExample(example);

        ComicDetailVO comicDetailVO = new ComicDetailVO();
        comicDetailVO.setChapterList(chapterList);
        comicDetailVO.setComic(comic);

        List<ComicVO> comicVOList = new ArrayList<>();
        //详细下的推荐漫画
        for (Comic comic1 : comicList) {
            ComicVO dto = new ComicVO();
            Chapter chapter = chapterDAO.selectNew(comic1.getId());
            dto.setChapter(chapter);
            dto.setComic(comic1);
            dto.setHost(comicConfig.getHost());
            comicVOList.add(dto);
        }
        comicDetailVO.setComicVOList(comicVOList);
        comicDetailVO.setHost(comicConfig.getHost());
        return Results.success(comicDetailVO);
    }

    @Override
    public Results switchStat(Boolean status, Integer id) {
        ChapterExample chapterExample = new ChapterExample();
        ChapterExample.Criteria criteria = chapterExample.createCriteria().andComicIdEqualTo(id);
        List<Chapter> chapterList = chapterDAO.selectByExample(chapterExample);
        if(chapterList != null)
        {
            for (Chapter chapter : chapterList) {
                chapter.setStatus(!status? 0:null);
                chapterDAO.updateByPrimaryKeySelective(chapter);
            }
        }

        Comic comic = comicDAO.selectByPrimaryKey(id);
        comic.setStatus(status? 1:0);
        comicDAO.updateByPrimaryKey(comic);
        return Results.success();
    }

    @Override
    public Results<ComicDetailVO> getTagAndType() {
        TagExample tagExample = new TagExample();
        tagExample.createCriteria().andStatusEqualTo(1);
        List<Tag> tagList = tagDAO.selectByExample(tagExample);

        TypeExample typeExample = new TypeExample();
        typeExample.createCriteria().andStatusEqualTo(1);
        List<Type> typeList = typeDAO.selectByExample(typeExample);

        ComicDetailVO vo = new ComicDetailVO();
        vo.setTagList(tagList);
        vo.setTypeList(typeList);
        return Results.success(vo);
    }
}
