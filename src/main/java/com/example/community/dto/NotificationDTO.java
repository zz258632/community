package com.example.community.dto;

import com.example.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Long time;
    private Integer isRead;
    private User notifier;
    private String outerContent;
    private String outerAction;
}
