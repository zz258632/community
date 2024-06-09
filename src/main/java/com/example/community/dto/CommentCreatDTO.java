package com.example.community.dto;

import lombok.Data;

@Data
public class CommentCreatDTO {
    private Integer parent_id;
    private Integer isChildComment;
    private String content;
}
