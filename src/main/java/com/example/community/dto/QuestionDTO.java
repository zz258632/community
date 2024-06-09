package com.example.community.dto;

import com.example.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String content;
    private Integer creator_uid;
    //
    private Long creat_time;
    private Long modified_time;
    //
    private Integer like_count;
    private Integer comment_count;
    private Integer view_count;
    private String tags;
    private User user;
}
