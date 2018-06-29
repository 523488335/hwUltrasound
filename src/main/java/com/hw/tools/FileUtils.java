package com.hw.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.hw.exception.ErrorCode;
import com.hw.exception.HwException;

import sun.misc.BASE64Decoder;
public class FileUtils {

	private FileUtils() throws HwException{
		throw new HwException(ErrorCode.编程错误, "静态工具类禁止实例化");
	}
	
	@SuppressWarnings("restriction")
	public static void saveImage(String imageFile, String dest, String filename) throws IOException{
        imageFile = imageFile.replaceAll("data:image/png;base64,", "");
        // 通过base64来转化图片
		BASE64Decoder decoder = new BASE64Decoder();
        // Base64解码
        byte[] imageByte = null;
        try {
            imageByte = decoder.decodeBuffer(imageFile);
            for (int i = 0; i < imageByte.length; ++i) {
                if (imageByte[i] < 0) {// 调整异常数据
                    imageByte[i] += 256;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 生成文件路径
        Resource r = new ClassPathResource("/static");
        File path = new File(r.getFile(), dest);
        if (!path.exists()) {
			path.mkdir();
		}
        try {
            // 生成文件
            File imageFile2 = new File(path, filename);
            System.out.println(imageFile2);
            imageFile2.createNewFile();
            if (!imageFile2.exists()) {
                imageFile2.createNewFile();
            }
            OutputStream imageStream = new FileOutputStream(imageFile2);
            imageStream.write(imageByte);
            imageStream.flush();
            imageStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static boolean deleteFile(String path){
		return deleteFile(new File(path));
	}
	public static boolean deleteFile(File file){
		return file.delete();
	}
}
