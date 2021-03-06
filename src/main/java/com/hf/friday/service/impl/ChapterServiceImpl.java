package com.hf.friday.service.impl;

import com.hf.friday.base.ResponseCode;
import com.hf.friday.base.Results;
import com.hf.friday.dao.ChapterDAO;
import com.hf.friday.dao.ComicDAO;
import com.hf.friday.dao.ImageDAO;
import com.hf.friday.model.*;
import com.hf.friday.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * @Author CoolWind
 * @Date 2020/4/18 13:26
 */
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Value("${comic.path}")
    private String comicPath;
    @Autowired
    private ChapterDAO chapterDAO;
    @Autowired
    private ImageDAO imageDAO;

    @Override
    public Results<Chapter> list(Integer offset, Integer limit, Integer id) {

        List<Chapter> list = chapterDAO.list(id, offset, limit);
        return list.size() > 0 ? Results.success("成功",list.size(),list) : Results.failure();
    }

    @Override
    public Results insert(Chapter chapter) {
        chapter.setNum(0);
        chapter.setStatus(0);
        chapter.setCreateTime(new Date());
        int x =chapterDAO.insert(chapter);
        return x == 1 ? Results.success() : Results.failure();
    }

    @Override
    public Results switchStat(Boolean status, Integer id) {
        Chapter chapter = new Chapter();
        chapter.setId(id);
        chapter.setStatus(status ? 1 : 0);
        return chapterDAO.updateByPrimaryKeySelective(chapter) == 1 ? Results.success():Results.failure();
    }

    @Override
    public Results deleteList(List<Integer> ids) {
        int num = 0;
        for (Integer id : ids) {
            Chapter chapter = chapterDAO.selectByPrimaryKey(id);
            if(chapter == null)
            {
                return Results.failure(ResponseCode.FAIL.getCode(),"删除的章节不存在!");
            }
            ImageExample example= new ImageExample();
            example.createCriteria().andTargetIdEqualTo(id);
            List<Image> imageList = imageDAO.selectByExample(example);
            if(imageList != null && imageList.size() != 0)
            {
                for (Image image : imageList) {
                    String url = image.getUrl();
                    String name = url.substring(7);
                    File file = new File(Paths.get(comicPath, name).toString());
                    //删除文件
                    if(file.exists() )
                    {
                        file.delete();
                    }
                    int i = imageDAO.deleteByPrimaryKey(image.getId());
                    num ++;
                }
            }
            chapterDAO.deleteByPrimaryKey(id);
        }
        return Results.success(num,null);
    }

    @Override
    public Results update(Chapter chapter) {
        int x = chapterDAO.updateByPrimaryKeySelective(chapter);
        return x==1 ? Results.success():Results.failure();
    }
}
