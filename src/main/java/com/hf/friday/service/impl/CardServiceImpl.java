package com.hf.friday.service.impl;

import com.hf.friday.base.Results;
import com.hf.friday.dao.CardDAO;
import com.hf.friday.dao.ImageDAO;
import com.hf.friday.model.Card;
import com.hf.friday.model.Image;
import com.hf.friday.service.CardService;
import com.hf.friday.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Author CoolWind
 * @Date 2020/5/4 17:01
 */
@Service
@Transactional
public class CardServiceImpl implements CardService {
    @Value("${comic.path}")
    private String comicPath;
    @Autowired
    private CardDAO cardDAO;
    @Autowired
    private ImageDAO imageDAO;

    /**
     *
     * @param text 内容
     * @param id 用户id
     * @param list 图片列表
     *             图片命名的规则  1.当前日期(毫秒数) + 6位随机数
     *             图片的保存路径  /comicPath/community/用户id/图片
     * @return
     */
    @Override
    public Results addCard(String text, Integer id, List<MultipartFile> list) throws IOException {
        Card card = new Card();
        card.setText(text);
        card.setCreateTime(new Date());
        card.setUserId(id);
        card.setStatus(1);
        card.setType(list.size() > 0 ? 1:0);
        cardDAO.insertSelective(card);
        if(list.size() != 0)
        {
            for (MultipartFile multipartFile : list) {
                long time = System.currentTimeMillis();
                Random random = new Random();
                int rdInt = random.nextInt(999999);
                //获取文件后缀名
                try {
                    String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf('.'));
                    String url = comicPath +"community/"+id+"/" + time + rdInt  + suffix;
                    File file = new File(url);
                    File fileParent = file.getParentFile();
                    if(!fileParent.exists()){
                        fileParent.mkdirs();
                    }
                    FileCopyUtils.copy(multipartFile.getInputStream(),new FileOutputStream(file));

                    Image image = new Image();
                    image.setUrl("/comic/" + "community/"+id+"/" + time + rdInt  + suffix);
                    image.setTargetId(card.getId());
                    image.setType(1);
                    int x = imageDAO.insert(image);
                } catch (Exception e) {
                    throw e;
                }

            }
        }
        return Results.success("发布成功");
    }
}
