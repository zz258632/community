package com.example.community.controller;

import com.example.community.dto.CommentCreatDTO;
import com.example.community.exception.myException;
import com.example.community.mapper.CommentMapper;
import com.example.community.mapper.NotificationMapper;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Comment;
import com.example.community.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    NotificationMapper notificationMapper;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreatDTO commentCreatDTO,
                       HttpServletRequest request) {
        String account = (String) request.getSession().getAttribute("account");
        Map<Object, Object> hm = new HashMap<>();
        if (account == null) {
            hm.put("message", "未登录不能进行评论");
            return hm;
        }
        Comment comment = new Comment();
        comment.setParent_id(commentCreatDTO.getParent_id());
        comment.setContent(commentCreatDTO.getContent());
        comment.setIsChildComment(commentCreatDTO.getIsChildComment());
        comment.setCreat_time(System.currentTimeMillis());
        comment.setModified_time(System.currentTimeMillis());
        comment.setCommentator_uid(userMapper.findByAccount(account).getUid());
        commentMapper.insert(comment);
        //创建通知
        Notification notification = new Notification();
        notification.setNotifier_uid(comment.getCommentator_uid());
        //一级评论
        if (commentCreatDTO.getIsChildComment() == 0) {
            questionMapper.addComment_count(comment.getParent_id());//评论数+1
            notification.setReceiver_uid(questionMapper.findById(comment.getParent_id()).getCreator_uid());
            notification.setTarget_id(comment.getParent_id());
            notification.setType(2);
            notification.setTime(System.currentTimeMillis());
            notificationMapper.insert(notification);
        }
        //二级评论
        else {
            notification.setReceiver_uid(commentMapper.findById(comment.getParent_id()).getCommentator_uid());
            notification.setTarget_id(comment.getParent_id());
            notification.setType(4);
            notification.setTime(System.currentTimeMillis());
            notificationMapper.insert(notification);
            //额外通知
            if (comment.getContent().startsWith("@")) {
                String[] accounts = comment.getContent().split(":@");
                accounts[0] = accounts[0].substring(1);
                if (accounts.length != 1)
                    accounts[accounts.length - 1] = accounts[accounts.length - 1].split(":")[0];
                else {
                    accounts=accounts[0].split(":");
                    accounts= new String[]{accounts[0]};
                }
                for (String a : accounts) {
                    Notification notificationExtra = new Notification();
                    notificationExtra.setNotifier_uid(comment.getCommentator_uid());
                    notification.setReceiver_uid(userMapper.findByAccount(a).getUid());
                    notification.setTarget_id(comment.getParent_id());
                    notificationExtra.setTime(System.currentTimeMillis());
                    notificationExtra.setType(4);
                    System.out.println(a);
                    notification.setTarget_id(userMapper.findByAccount(a).getUid());
                    notificationMapper.insert(notificationExtra);
                }
            }
        }
        hm.put("message", "评论成功");
        return hm;
    }

    @ResponseBody
    @RequestMapping(value = "/commentLike", method = RequestMethod.POST)
    public void post(HttpServletRequest request,
                     @RequestBody Map<String, Object> requestBody) {
        commentMapper.addLike_count((Integer) requestBody.get("id"));
    }
}
