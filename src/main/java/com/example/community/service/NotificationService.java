package com.example.community.service;

import com.example.community.dto.NotificationDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.CommentMapper;
import com.example.community.mapper.NotificationMapper;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Comment;
import com.example.community.model.Notification;
import com.example.community.model.Question;
import com.example.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    CommentMapper commentMapper;

    public static Integer size = 7;

    public List<NotificationDTO> getNotifications(String account, Integer page) {
        Integer offset = size * (page - 1);
        User user = userMapper.findByAccount(account);
        List<Notification> notifications = notificationMapper.findByReceiverUid_limit(user.getUid(), offset, size);
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            notificationDTOS.add(notification2dto(notification));
        }
        return notificationDTOS;
    }



    private NotificationDTO notification2dto(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setNotifier(userMapper.findByUid(notification.getNotifier_uid()));
        switch (notification.getType()) {
            case 1: {
                notificationDTO.setOuterAction("点赞了你的问题");
                notificationDTO.setOuterContent(questionMapper.findById(notification.getTarget_id()).getTitle());
                break;
            }
            case 2: {
                notificationDTO.setOuterAction("回复了你的问题");
                notificationDTO.setOuterContent(questionMapper.findById(notification.getTarget_id()).getTitle());
                break;
            }
            case 3: {
                notificationDTO.setOuterAction("点赞了你的评论");
                notificationDTO.setOuterContent(questionMapper.findById(notification.getTarget_id()).getTitle());
                break;
            }
            case 4: {
                notificationDTO.setOuterAction("回复了你的评论");
                notificationDTO.setOuterContent(questionMapper.findById(notification.getTarget_id()).getTitle());
                break;
            }
        }
        notificationDTO.setTime(notification.getTime());
        notificationDTO.setIsRead(notification.getIsRead());
        return notificationDTO;
    }

    public Integer getNewNotificationsCount(String account) {
        Integer uid=userMapper.findByAccount(account).getUid();
        return notificationMapper.countUnreadByReceiverUid(uid);
    }
}
