package com.example.community.model;

import lombok.Data;

@Data
public class Notification {
    private Integer id;
    private Integer notifier_uid;
    private Integer receiver_uid;
    private Integer target_id;
    private Integer type;//1：点赞问题，2：回复问题，3：点赞评论，4：回复评论
    private Long time;
    private Integer isRead;
}
