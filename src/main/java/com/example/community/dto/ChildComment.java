package com.example.community.dto;

import com.example.community.model.User;
import lombok.Data;

@Data
public class ChildComment {
    private Integer id;
    private Integer parent_id;
    private Integer isChildComment;
    private Integer commentator_uid;
    private Long creat_time;
    private Long modified_time;
    private Integer like_count;
    private String content;
    private User user;
}
