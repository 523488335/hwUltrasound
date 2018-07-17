package com.hw.controller;

import com.hw.exception.HwException;
import com.hw.model.User;
import com.hw.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class UserController {
	
    @Autowired
    private UserService userService;

    /**
     * @deprecated 查询用户表所有用户
     * @param url /getAllUser
     */
    @RequestMapping("getAllUser")
    public Object getAllUser(){
        System.out.println("------------查询用户表所有用户------------");
        return userService.getAllUser();
    }

    /**
     * @deprecated 查询用户：条件Id
     * @param url /getAllUser
     * @param Id 用户Id
     */
    @RequestMapping("getUserById")
    public User getUserById(User user){
        System.out.println("------------查询Id="+user.getUserId()+"的用户------------");
        return userService.getUserById(user.getUserId());
    }


    /**
     * @deprecated 登入
     * @param url /getUserByUser
     * @param username 用户名
     * @param password 密码
     * @return 是否登入成功
     */
    @RequestMapping("getUserByUser")
    public Object getUserByUser(HttpSession session,User user) throws ServletException, IOException {
        try {
			return userService.Login(session, user.getUsername(), user.getPassword());
		} catch (HwException e) {
			return e.toString();
		}
    }

    /**
     * @deprecated 注销
     * @param url /LogOut
     */
    @RequestMapping("LogOut")
    public void LogOut(HttpSession session){
    	userService.LogOut(session);
    }
    /**
     * @deprecated 获取登入用户信息
     * @param url /getSession
     */
    @RequestMapping("getSession")
    public Object getSession(HttpSession session){
        return session.getAttribute("user");
    }
    
//    @RequestMapping("Login")
//    public String Login(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
//        request.setCharacterEncoding("utf-8");
//        //接收参数
//        String userName = user.getUsername();
//        String userPwd = user.getPassword();
//        Map<String, String> result = new HashMap<>();
//        result.put("username",userName);
//        result.put("password",userPwd);
//        System.out.println("------------查询username="+user.getUsername()+"的用户------------");
//        List<User> li = userMapper.selectUserByUser(result);
//        //判断是否登陆成功
//        if(li != null && li.size() != 0){
//            HttpSession session = request.getSession();
//            session.setAttribute("user", li.get(0));
//            return "success";
//        }else{
//            //登入error
//            return "error";
//        }
//    }

}
