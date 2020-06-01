package com.hf.friday.service.impl;

import com.hf.friday.base.Constants;
import com.hf.friday.base.Results;
import com.hf.friday.dao.SwiperImgDAO;
import com.hf.friday.model.Image;
import com.hf.friday.model.SwiperImg;
import com.hf.friday.model.SwiperImgExample;
import com.hf.friday.service.SwiperImgService;
import com.hf.friday.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

/**
 * @Author CoolWind
 * @Date 2020/5/31 16:50
 */
@Service
@Transactional
public class SwiperImgServiceImpl implements SwiperImgService {
    @Autowired
    SwiperImgDAO swiperImgDAO;
    @Value("${comic.path}")
    private String comicPath;

    @Override
    public Results select() {
        SwiperImgExample example = new SwiperImgExample();
        example.createCriteria().andStatusEqualTo(Constants.VALID);
        List<SwiperImg> list = swiperImgDAO.selectByExample(example);
        return Results.success("success",list.size(),list);
    }

    @Override
    public Results insert(ImageVO vo) {
        SwiperImg swiperImg = new SwiperImg();
        swiperImg.setUrl(vo.getUrl());
        swiperImg.setName(vo.getName());
        swiperImg.setDescription(vo.getDescription());
        int i = swiperImgDAO.insertSelective(swiperImg);
        if(i == 1)
        {
            MultipartFile img = vo.getFile();
            long time = System.currentTimeMillis();
            Random random = new Random();
            int rdInt = random.nextInt(999999);

            //获取文件后缀名
            try {
                String suffix = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf('.'));
                String url = comicPath +"swiper/" + time + rdInt  + suffix;
                File file = new File(url);
                File fileParent = file.getParentFile();
                if(!fileParent.exists()){
                    fileParent.mkdirs();
                }
                FileCopyUtils.copy(img.getInputStream(),new FileOutputStream(file));

                swiperImg.setSrc("/comic/" + "swiper/" + time + rdInt  + suffix);
                swiperImgDAO.updateByPrimaryKeySelective(swiperImg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Results.success();
        }else
        {
            return Results.failure();
        }
    }
}
