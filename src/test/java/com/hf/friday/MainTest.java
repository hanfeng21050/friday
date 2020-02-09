package com.hf.friday;

import com.hf.friday.util.ThumbnailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


public class MainTest {

    @Test
    public void ThumbnailTest()
    {
        ThumbnailUtil.storeThumbnail("E:/upload/35c82086-bc58-4be9-9cc3-3d6e28f2be8b.png","E:/upload/thumbnail/300X200-1.jpg");
    }
}
