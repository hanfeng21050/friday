package com.hf.friday;


import com.hf.friday.dao.ComicDAO;
import com.hf.friday.dao.TagDAO;
import com.hf.friday.model.Comic;
import com.hf.friday.model.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTest {
    @Autowired
    ComicDAO comicDAO;
    @Autowired
    TagDAO tagDao;

    @Test
    public void ThumbnailTest() {
        Comic comic = new Comic();
        comic.setTitle("1111");
        comicDAO.insertSelective(comic);
        System.out.println(comic);
    }

    @Test
    public void test1() {
        Tag tag = new Tag();
        tag.setName("111");
        tagDao.insertSelective(tag);
        System.out.println(tag.getId());
    }
}
