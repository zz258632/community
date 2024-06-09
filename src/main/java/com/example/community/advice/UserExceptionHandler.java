package com.example.community.advice;

import com.example.community.exception.myException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(myException.class)
    ModelAndView handle(Throwable e,Model model){
        if(e instanceof myException){
            model.addAttribute("message",e.getMessage());
        }
        else {
            model.addAttribute("message","服务器冒烟了，要不你稍后试试");
        }
        return new ModelAndView("error");
    }
}
