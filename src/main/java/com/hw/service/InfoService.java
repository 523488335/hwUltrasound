package com.hw.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hw.bean.Point;
import com.hw.exception.ErrorCode;
import com.hw.exception.HwException;
import com.hw.model.Log;

@Service
public class InfoService {

	public Log parseLog(String path) throws FileNotFoundException, HwException{
		File file = new File(path);
		if (!file.exists()) {
			throw new HwException(ErrorCode.非法参数, "不存在该目录");
		}
		String logPath = null;
		for(String p : file.list()){
			if (p.contains(".log")) {
				logPath = p;
				break;
			}
		}
		if (logPath == null) {
			throw new HwException(ErrorCode.非法参数, "目录下无日志文件");
		}
		FileInputStream in = new FileInputStream(logPath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		return null;
	}
	
	public List<Point> pasePointSet(String path) throws HwException{
		List<Point> list = new ArrayList<>();
		try{
			File file = new File(path);
			System.out.println(path);
			if (!file.exists()) {
				throw new HwException(ErrorCode.非法参数, "文件不存在");
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
}
