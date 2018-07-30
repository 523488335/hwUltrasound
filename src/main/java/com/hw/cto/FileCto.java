package com.hw.cto;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hw.exception.HwException;

@RestController
@RequestMapping("/file")
public class FileCto {

	/**
     * @deprecated 下载文件
     * @param url /file/downloads
     * @param path 文件路径
     * @return 文件输出流
	 * @throws HwException 
     */
    @RequestMapping("/downloads")
    public void downloads(HttpServletRequest request,HttpServletResponse response) throws HwException{
    	String path = request.getParameter("path");
    	File file = new File(path);
        if(file.exists()){ //判断文件父目录是否存在
        	if(path.lastIndexOf("/") != -1){
        		path = path.substring(path.lastIndexOf("/"));
        	}
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;filename=" + path);
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
            }
            System.out.println("----------file download" + path);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * @deprecated 浏览器打开文件
     * @param url /file/open
     * @param path 文件路径
     * @return 文件输出流
	 * @throws HwException 
     */
    @RequestMapping("/open")
    public void open(HttpServletRequest request,HttpServletResponse response) throws HwException{
    	String path = request.getParameter("path");
    	File file = new File(path);
        if(file.exists()){ //判断文件父目录是否存在
        	if(path.lastIndexOf("/") != -1){
        		path = path.substring(path.lastIndexOf("/"));
        	}
            response.setContentType("text/html");
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
            }
            System.out.println("----------file download" + path);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
