package com.hf.friday;


import com.hf.friday.dao.ComicDAO;
import com.hf.friday.model.Comic;
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
    @Test
    public void ThumbnailTest()
    {
        Comic comic = new Comic();
        comic.setTitle("1111");
        comicDAO.insertSelective(comic);
        System.out.println(comic);
    }
}
