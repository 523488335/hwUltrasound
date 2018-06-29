//package com.hw.tools;
//
//import sun.misc.BASE64Decoder;
//
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Random;
//
///**
// * Created by meifan on 2018/4/25.
// */
//public class PictureConversion {
//    public static void main(String[] args) throws IOException {
//        //因为字符串长度超过String最大限度，所以用IO流读取文件
//        FileReader fr = new FileReader("/Users/meifan/Desktop/img.txt");
//        BufferedReader br = new BufferedReader(fr);
//        //存储到s中
//        String imageFile = br.readLine();
//        imageFile = imageFile .replaceAll("data:image/png;base64,", "");
//        // 通过base64来转化图片
//        BASE64Decoder decoder = new BASE64Decoder();
//        // Base64解码
//        byte[] imageByte = null;
//        try {
//            imageByte = decoder.decodeBuffer(imageFile);
//            for (int i = 0; i < imageByte.length; ++i) {
//                if (imageByte[i] < 0) {// 调整异常数据
//                    imageByte[i] += 256;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // 生成文件名
//        String files = new SimpleDateFormat("yyyyMMddHHmmssSSS")
//                .format(new Date())
//                + (new Random().nextInt(9000) % (9000 - 1000 + 1) + 1000)
//                + ".png";
//        // 生成文件路径
//        String filename = "/Users/meifan/Desktop/puboimg/"+files;
//        try {
//            // 生成文件
//            File imageFile2 = new File(filename);
//            imageFile2.createNewFile();
//            if (!imageFile2.exists()) {
//                imageFile2.createNewFile();
//            }
//            OutputStream imageStream = new FileOutputStream(imageFile2);
//            imageStream.write(imageByte);
//            imageStream.flush();
//            imageStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
