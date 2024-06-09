package com.example.community.controller;

import com.example.community.dto.NotificationDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.NotificationMapper;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.service.NotificationService;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page" ,defaultValue = "1")Integer page){
        String account=(String) request.getSession().getAttribute("account");
        int maxPage=0;
        if("myQuestions".equals(action)){
            model.addAttribute("selection","myQuestions");
            model.addAttribute("selectionName","我的问题");
            List<QuestionDTO> myQuestions=questionService.myQuestions(account,page);
            if(!myQuestions.isEmpty())
                maxPage = (int)(1.0*questionMapper.countByUid(myQuestions.get(0).getCreator_uid())/QuestionService.size+0.9);
            model.addAttribute("myPage",page);
            model.addAttribute("myQuestions",myQuestions);
            model.addAttribute("mq_maxPage",maxPage);
        }
        else if("notifications".equals(action)){
            Integer uid=userMapper.findByAccount(account).getUid();
            List<NotificationDTO> notifications=notificationService.getNotifications(account,page);
            model.addAttribute("selection","notifications");
            model.addAttribute("selectionName","最新消息");
            if(!notifications.isEmpty())
                maxPage = (int)(1.0*notificationMapper.countByReceiverUid(uid)/NotificationService.size+0.9);
            model.addAttribute("myPage",page);
            model.addAttribute("myNotifications",notifications);
            model.addAttribute("mn_maxPage",maxPage);
        }
        else return "error";
        return "profile";
    }
}
