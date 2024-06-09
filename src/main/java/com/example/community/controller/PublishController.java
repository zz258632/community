package com.example.community.controller;

import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import com.example.community.service.LoginService;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
public class PublishController {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       HttpServletRequest request) {
        Question question = questionMapper.findById(id);
        request.getSession().setAttribute("title", question.getTitle());
        request.getSession().setAttribute("tags", question.getTags());
        request.getSession().setAttribute("content", question.getContent());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(HttpServletRequest request,
                          Model model,
                          @RequestParam(name = "id", defaultValue = "0") Integer id) {
        String account = (String) request.getSession().getAttribute("account");
        if (account != null) {
            if (id != 0) {
                Question question = questionMapper.findById(id);
                request.getSession().setAttribute("id",id);
                request.getSession().setAttribute("title", question.getTitle());
                request.getSession().setAttribute("tags", question.getTags());
                request.getSession().setAttribute("content", question.getContent());
            }
            request.getSession().setAttribute("publish", null);
            return "publish";
        } else {
            model.addAttribute("login", "failure");
            return "forward:/";
        }
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(name = "title", defaultValue = "") String title,
                            @RequestParam(name = "content", defaultValue = "") String content,
                            @RequestParam(name = "tags", defaultValue = "") String tags,
                            HttpServletRequest request,
                            Model model) {
        request.getSession().setAttribute("title", title);
        request.getSession().setAttribute("tags", tags);
        request.getSession().setAttribute("content", content);
        request.getSession().setAttribute("publish", null);
        if (!(title.equals("") || content.equals("") || tags.equals(""))) {
            Question question;
            Integer id = 0;
            if (request.getSession().getAttribute("id") != null)
                id = (Integer) request.getSession().getAttribute("id");
            if (id == 0) {
                question = new Question();
                question.setTitle(title);
                question.setContent(content);
                //处理标签
                String[] tagsSplit =tags.split("/|");
                StringBuilder tagsBuilder = new StringBuilder();
                for (int i = 0; i < tagsSplit.length; i++) {
                    tagsSplit[i]=tagsSplit[i].trim();
                    tagsBuilder.append(tagsSplit[i]);
                }
                tags = tagsBuilder.toString();
                question.setTags(tags);
                question.setCreat_time(String.valueOf(System.currentTimeMillis()));
                question.setModified_time(String.valueOf(System.currentTimeMillis()));
                String account = (String) request.getSession().getAttribute("account");
//            System.out.println(account);
                Integer uid = userMapper.findByAccount(account).getUid();
                question.setCreator_uid(uid);
                questionMapper.insert(question);
            } else {
                question = questionMapper.findById(id);
                question.setTitle(title);
                question.setContent(content);
                question.setTags(tags);
                question.setModified_time(String.valueOf(System.currentTimeMillis()));
                questionMapper.update(question);
            }
            request.getSession().setAttribute("publish", "success");
        } else if (title.equals("")) {
            request.getSession().setAttribute("tags", tags);
            request.getSession().setAttribute("content", content);
            request.getSession().setAttribute("publish", "标题不能为空");
        } else if (content.equals("")) {
            request.getSession().setAttribute("tags", tags);
            request.getSession().setAttribute("title", title);
            request.getSession().setAttribute("publish", "内容不能为空");
        } else if (tags.equals("")) {
            request.getSession().setAttribute("title", title);
            request.getSession().setAttribute("content", content);
            request.getSession().setAttribute("publish", "标签不能为空");
        }
        return "publish";
    }

}
