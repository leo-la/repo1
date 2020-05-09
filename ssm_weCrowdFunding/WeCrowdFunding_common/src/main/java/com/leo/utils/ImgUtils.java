package com.leo.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;

/**
 * 图片工具
 */
public class ImgUtils {
    /**
     * 客户端显示图片
     * @param staticAccessPath
     * @param img_name
     * @return
     */
    public static ResponseEntity<byte[]> showImg(String staticAccessPath,String img_name){
        String path = staticAccessPath+img_name+".png";
        byte[] imageContent = null;
        try {
            imageContent = FileUtils.fileToByte(new File(path));
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
