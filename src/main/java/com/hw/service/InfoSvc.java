package com.hw.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.hw.bean.Point;
import com.hw.bean.Point2d;
import com.hw.exception.ErrorCode;
import com.hw.exception.HwException;


@Service
public class InfoSvc {
	public final static String BREATHE_FILE_LEFT = "chest_left"; 
	public final static String BREATHE_FILE_CENTER = "chest_center"; 
	public final static String BREATHE_FILE_RIGHT = "chest_right"; 
	
	public final static String POINT_3D = "3d_cloud";

	public List<String> parsePath(String path) throws FileNotFoundException, HwException{
		List<String> list = new ArrayList<>();
		File file = new File(path);
		System.out.println("=========" + path + "===========");
		if (!(file.exists() && file.isDirectory())) {
			throw new HwException(ErrorCode.非法参数, "参数必须为目录路径");
		}
		for(String p : file.list()){
			if (p.contains(".txt") || p.contains(".log")) {
				list.add(p);
			}
		}
		return list;
	}
	
	public List<Point> pasePointSet(String path) throws HwException{
		List<Point> list = new ArrayList<>();
		try{
			File file = new File(path);
			System.out.println(path);
			if (!file.exists() || !file.isDirectory()) {
				throw new HwException(ErrorCode.非法参数, "目录不存在");
			}
			for(String str : file.list()){
				if (str.contains(POINT_3D)) {
					file = new File(file, str);
				}
			}
			InputStream in = new FileInputStream(file);
			LineNumberReader reader = new LineNumberReader(new InputStreamReader(in));
			String line = reader.readLine();
			while(line != null){
				String[] strs = line.split(" ");
				Point point = new Point(strs[0],strs[1],strs[2]);
				list.add(point);
				line = reader.readLine();
			}
			in.close();
			reader.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Point2d> pasePoint2dSet(String path,String fileName) throws HwException{
		List<Point2d> list = new ArrayList<>();
		try{
			File file = new File(path);
			if (!file.exists() || !file.isDirectory()) {
				throw new HwException(ErrorCode.非法参数, "目录不存在");
			}
			for(String str : file.list()){
				if (str.contains(fileName)) {
					file = new File(file, str);
				}
			}
			InputStream in = new FileInputStream(file);
			LineNumberReader reader = new LineNumberReader(new InputStreamReader(in));
			Date date = new Date();
			String line = reader.readLine();//去除第一行
			line = reader.readLine();
			while(line != null){
				String[] strs = line.split(",");
				int index = strs[2].indexOf(".");
				long time = Long.parseLong(strs[2].substring(0, index) + strs[2].substring(index + 1, index + 4));
				Point2d point = new Point2d(date.getTime() + time,Float.parseFloat(strs[1]));
				list.add(point);
				line = reader.readLine();
			}
			in.close();
			reader.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list.subList(3, list.size() - 2);//去除后两行
	}
	/**
	 * 文件下载
	 * @param filename 文件名
	 * @param response 响应
	 * @return
	 */
	public boolean fileDownload(String filename,HttpServletResponse response){
        File file = new File(filename);
        if(file.exists()){ //判断文件父目录是否存在
        	if(filename.lastIndexOf("/") != -1){
        		filename = filename.substring(filename.lastIndexOf("/"));
        	}
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);
            response.setHeader("Accept-Ranges", "bytes");
            response.setContentLengthLong(file.length());
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer,0,i);
                    i = bis.read(buffer);
                }
                os.write(new byte[]{13,10});
                os.flush();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
            System.out.println("----------file download" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return true;
	}
}
