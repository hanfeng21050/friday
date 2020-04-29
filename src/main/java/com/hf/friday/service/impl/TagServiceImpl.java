package com.hf.friday.service.impl;

import com.hf.friday.base.Constants;
import com.hf.friday.base.Results;
import com.hf.friday.dao.TagDAO;
import com.hf.friday.model.*;
import com.hf.friday.service.TagService;
import com.hf.friday.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Author CoolWind
 * @Date 2020/4/23 13:17
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDAO tagDAO;
    @Value("${comic.path}")
    private String comicPath;

    @Override
    public Results<Tag> listByPage(Integer offset, Integer limit) {
        TagExample tagExample = new TagExample();
        tagExample.setOffset((long)offset);
        tagExample.setLimit(limit);
        List<Tag> tagList = tagDAO.selectByExample(tagExample);

        tagExample.clear();
        int count = (int) tagDAO.countByExample(tagExample);

        return Results.success("success",count,tagList);
    }

    @Override
    public Results switchStat(Boolean status, Integer id) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setStatus(status ? 1 : 0);
        return tagDAO.updateByPrimaryKeySelective(tag) == 1 ? Results.success():Results.failure();
    }

    @Override
    public Results addComic(ImageVO vo) throws IOException {
        Tag tag = new Tag();
        tag.setName(vo.getName());
        //返回主键
        int i = tagDAO.insertSelective(tag);
        if(i == 1)
        {
            MultipartFile img = vo.getFile();
            String extName = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
            String url = comicPath+"/"+"tag/"+ +tag.getId()+extName;
            File file = new File(url);
            File fileParent = file.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            FileCopyUtils.copy(img.getInputStream(),new FileOutputStream(file));
            tag.setSrc("/comic/tag/"+ +tag.getId()+extName);
            tagDAO.updateByPrimaryKeySelective(tag);

            return Results.success();
        }else
        {
            return Results.failure();
        }
    }
}
