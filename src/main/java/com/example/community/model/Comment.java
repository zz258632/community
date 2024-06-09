package com.example.community.model;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private Integer parent_id;
    private Integer isChildComment;
    private Integer commentator_uid;
    private Long creat_time;
    private Long modified_time;
    private Integer like_count;
    private String content;
}
