package com.hw.cto;

import com.hw.exception.HwException;
import com.hw.model.User;
import com.hw.service.UserSvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class UserCto {
	
    @Autowired
    private UserSvc userSvc;

    /**
     * @deprecated 查询用户表所有用户
     */
    @RequestMapping("getAllUser")
    public Object getAllUser(){
        System.out.println("------------查询用户表所有用户------------");
        return userSvc.getAllUser();
    }

    /**
     * @deprecated 查询用户：条件Id
     * @param Id 用户Id
     */
    @RequestMapping("getUserById")
    public User getUserById(User user){
        System.out.println("------------查询Id="+user.getUserId()+"的用户------------");
        return userSvc.getUserById(user.getUserId());
    }


    /**
     * @deprecated 登入
     * @param username 用户名
     * @param password 密码
     * @return 是否登入成功
     */
    @RequestMapping("getUserByUser")
    public Object getUserByUser(HttpSession session,User user) throws ServletException, IOException {
        try {
			return userSvc.Login(session, user.getUsername(), user.getPassword());
		} catch (HwException e) {
			return e.toString();
		}
    }

    /**
     * @deprecated 注销
     */
    @RequestMapping("LogOut")
    public void logOut(HttpSession session){
    	userSvc.invalidSession(session);
    }
    
    /**
     * @deprecated 获取登入用户信息
     */
    @RequestMapping("getSession")
    public Object getSession(HttpSession session){
        return session.getAttribute("user");
    }
    
}
