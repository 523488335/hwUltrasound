package com.hw.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.hw.exception.ErrorCode;
import com.hw.exception.HwException;

import sun.misc.BASE64Decoder;

public class FileUtils {

	private FileUtils() throws HwException{
		throw new HwException(ErrorCode.编程错误, "静态工具类禁止实例化");
	}
	
	@SuppressWarnings("restriction")
	public static void saveImage(String imageFile, File file) throws IOException{
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
        try {
            if (!file.exists()) {
            	System.out.println(file.getAbsolutePath()); 
            	file.createNewFile();
            }
            OutputStream imageStream = new FileOutputStream(file);
            imageStream.write(imageByte);
            imageStream.flush();
            imageStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	


//	public static void main(String[] args) throws Exception {
//
//		System.out.println("start...");
//
//		String saveMp4name = "C:/Users/lee2/Desktop/images/f1.flv"; //保存的视频名称
//
//		// 目录中所有的图片，都是jpg的，以1.jpg,2.jpg的方式，方便操作
//
//		String imagesPath = "C:/Users/lee2/Desktop/images/test_hkws/"; // 图片集合的目录
//
//		test(saveMp4name,imagesPath);
//
//		System.out.println("end...");
//
//	}

 

//	public static void test(String saveMp4name,String imagesPath) throws Exception  {
//
//		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(saveMp4name,640,480);
//
////		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 28
//
//		recorder.setVideoCodec(avcodec.AV_CODEC_ID_FLV1); // 28
//
////		recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4); // 13
//
//	 	recorder.setFormat("flv");
//
//	//	recorder.setFormat("mov,mp4,m4a,3gp,3g2,mj2,h264,ogg,MPEG4");
//
//		recorder.setFrameRate(20);
//
//	 	recorder.setPixelFormat(0); // yuv420p
//
//		recorder.start();
//
//		//
//
//		OpenCVFrameConverter.ToIplImage conveter = new OpenCVFrameConverter.ToIplImage();
//
//		// 列出目录中所有的图片，都是jpg的，以1.jpg,2.jpg的方式，方便操作
//
//		File file = new File(imagesPath);
//
//		File[] flist = file.listFiles();
//
//		// 循环所有图片
//
//		for(int i = 1; i <= flist.length; i++ ){
//
//			String fname = imagesPath+i+".jpg";
//
//			IplImage image = cvLoadImage(fname); // 非常吃内存！！
//
//			recorder.record(conveter.convert(image));
//
//			// 释放内存？ cvLoadImage(fname); // 非常吃内存！！
//
//			opencv_core.cvReleaseImage(image);
//
//		}
//
//		recorder.stop();
//
//		recorder.release();
//
//	}


	
	public static boolean deleteFile(String path){
		return deleteFile(new File(path));
	}
	public static boolean deleteFile(File file){
		return file.delete();
	}
}
