package com.hw.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hw.dao.UserDao;
import com.hw.exception.ErrorCode;
import com.hw.exception.HwException;
import com.hw.model.User;

@Service
public class UserSvc {
	
	@Autowired
	private UserDao userDao;
	
	public List<User> Login(HttpSession session, String username, String password) throws HwException{
		List<User> list = null;
		if(username == null || password == null){
			throw new HwException(ErrorCode.非法参数, "用户名和密码不能为null");
		}
		list = getUser(username);
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
	
	public List<User> getUser(String username){
		return userDao.findByUsername(username);
	}
	
	public void invalidSession(HttpSession session){
    	session.invalidate();
    }
	
	public User getUserById(Integer id){
        return userDao.findByUserId(id).get(0);
    }
	
	public List<User> getAllUser(){
        return userDao.findAll();
    }
}
