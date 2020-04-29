package com.leo.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * 文件工具
 */
public class FileUtils {
    /**
     * 加载文件
     * @param file
     * @param filename
     * @param filePath
     * @throws Exception
     */
    public static void uploadFile(MultipartFile file, String filename, String filePath) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        file.transferTo(new File(filePath,filename));
    }

    /**
     * 转换文件至字节码数组
     * @param img
     * @return
     * @throws Exception
     */
    public static byte[] fileToByte(File img) throws Exception {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedImage bi;
            bi = ImageIO.read(img);
            ImageIO.write(bi, "png", baos);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baos.close();
        }
        return bytes;
    }
}
