package com.example.community.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String content;
    private Integer creator_uid;
    private String creat_time;
    private String modified_time;
    private Integer like_count;
    private Integer comment_count;
    private Integer view_count;
    private String tags;
}
