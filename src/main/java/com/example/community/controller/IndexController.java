package com.example.community.controller;

import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.service.LoginService;
import com.example.community.service.NotificationService;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "search", required = false) String search) {
        HttpSession session = request.getSession();
        String account = (String) session.getAttribute("account");
        List<QuestionDTO> questionDTOS;
        Integer maxPage;
        if (search == null || "".equals(search.trim())) {
            questionDTOS = questionService.list(page);
            maxPage = (int) (1.0 * questionMapper.count() / QuestionService.size + 0.9);
        } else {
            model.addAttribute("search", search);
            questionDTOS = questionService.listBySearch(search, page);
            maxPage = (int) (1.0 * questionMapper.countBySearch(search) / QuestionService.size + 0.9);
        }
        if (account != null) {

            session.setAttribute("newNotificationsCount", notificationService.getNewNotificationsCount(account));
        }
        model.addAttribute("page", page);
        model.addAttribute("questions", questionDTOS);
        model.addAttribute("maxPage", maxPage);
        return "index";
    }


}
