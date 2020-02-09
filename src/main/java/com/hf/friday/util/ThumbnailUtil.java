package com.hf.friday.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

/**
 * 将文件转换为缩略图再保存
 */
public class ThumbnailUtil {
    /**
     * @param standardImgPath 原图片path
     * @param thumbnailImgPath 缩略图path
     */
    public static String storeThumbnail(String standardImgPath, String thumbnailImgPath) {
        File file = new File(standardImgPath);
        if(file!=null&&file.isFile()){
            try {
                File outFIle = new File(thumbnailImgPath);
                Thumbnails.of(file).size(400, 300).toFile(outFIle);
                return outFIle.getName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
