package com.hw.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hw.dao.UserMapper;
import com.hw.exception.ErrorCode;
import com.hw.exception.HwException;
import com.hw.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public List<User> Login(HttpSession session, String username, String password) throws HwException{
		List<User> list = null;
		if(username == null || password == null){
			throw new HwException(ErrorCode.非法参数, "用户名和密码不能为null");
		}
		list = userMapper.findByUsername(username);
		if (list == null || list.size() == 0) {
			throw new HwException(ErrorCode.流程出错, "账户不存在");
		}
		if (!password.equals(list.get(0).getPassword())) {
			throw new HwException(ErrorCode.流程出错, "密码错误");
		}
        session.setAttribute("user", list);
        session.setMaxInactiveInterval(1800);
        return list;
	}
	
	public void LogOut(HttpSession session){
    	session.invalidate();
    }
	
	public User getUserById(Integer id){
        return userMapper.findById(id).get(0);
    }
	
	public List<User> getAllUser(){
        System.out.println("------------查询用户表所有用户------------");
        return userMapper.findAll();
    }
}
