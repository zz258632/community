package com.example.community.controller;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/login")
    public String login(@RequestParam(name = "account",defaultValue = "") String account,
                        @RequestParam(name = "password",defaultValue = "") String password,
                        Model model,
                        HttpServletRequest request,
                        HttpServletResponse response){
        model.addAttribute("login",null);
        if(!account.equals("")&&!password.equals("")){
            if(userMapper.findByAccount(account)!=null&&password.equals(userMapper.findByAccount(account).getPassword())){
                response.addCookie(new Cookie("account",account));
//                User user=new User();
//                user.setAccount(account);
//                user.setPassword(password);
//                user.setCreat_time(String.valueOf(System.currentTimeMillis()));
//                user.setModified_time(String.valueOf(System.currentTimeMillis()));
//                userMapper.insert(user);
                return "redirect:/";
            }
            else {
                model.addAttribute("login","failure");
            }
        }

        return "login";
    }

    @GetMapping("/loginOut")
    public String loginOut(HttpServletRequest request,
                         HttpServletResponse response){
        LoginService.loginOut(response, request, userMapper);
        return "redirect:/";
    }



}
