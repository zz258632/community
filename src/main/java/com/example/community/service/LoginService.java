package com.example.community.service;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {

    static void loginOut(HttpServletResponse response, HttpServletRequest request, UserMapper userMapper) {
        request.getSession().removeAttribute("account");
        Cookie cookies[]=request.getCookies();
        if(cookies!=null)
            for (Cookie cookie:cookies) {
                if(cookie.getName().equals("account")){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
    }
}
