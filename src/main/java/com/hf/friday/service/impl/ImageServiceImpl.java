package com.hf.friday.service.impl;

import com.hf.friday.base.Results;
import com.hf.friday.dao.ChapterDAO;
import com.hf.friday.dao.ComicDAO;
import com.hf.friday.dao.ImageDAO;
import com.hf.friday.vo.ImageVO;
import com.hf.friday.model.Chapter;
import com.hf.friday.model.Comic;
import com.hf.friday.model.Image;
import com.hf.friday.service.ImageService;
import com.hf.friday.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @Author CoolWind
 * @Date 2020/4/18 14:27
 */
@Service
@Transactional
public  class ImageServiceImpl implements ImageService {
    @Autowired
    private ChapterDAO chapterDAO;
    @Autowired
    private ImageDAO imageDao;
    @Autowired
    private ComicDAO comicDAO;
    @Value("${comic.path}")
    private String comicPath;
    @Override
    public synchronized Results upload(ImageVO dto) throws IOException {
        Chapter chapter = chapterDAO.selectByPrimaryKey(dto.getId());
        Comic comic = comicDAO.selectByPrimaryKey(chapter.getComicId());

        //获取文件名
        MultipartFile img = dto.getFile();
        String imgName = comic.getNo() + img.getOriginalFilename();

        //图片保存路径
        String title = chapter.getTitle();
        String url = comicPath+comic.getNo()+"/"+StringUtil.genNO(chapter.getIndex())+"/"+imgName;


        File file = new File(url);
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }

        FileCopyUtils.copy(img.getInputStream(),new FileOutputStream(file));

        Image image = new Image();
        image.setUrl("/comic/" + comic.getNo()+"/"+ StringUtil.genNO(chapter.getIndex()) +"/"+imgName);
        image.setChapterId(dto.getId());
        int x = imageDao.insert(image);

        //更新数量
        chapter.setNum(chapter.getNum() + 1);
        if(chapter.getUploadTime() == null)
        {
            chapter.setUploadTime(new Date());
        }
        int y = chapterDAO.updateByPrimaryKeySelective(chapter);
        return x == 1 ? Results.success() : Results.failure();
    }
}
