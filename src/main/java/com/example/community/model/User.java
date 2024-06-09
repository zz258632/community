package com.example.community.model;

import lombok.Data;

@Data
public class User {
    private Integer uid;
    private String account;
    private String password;
    private String creat_time;
    private String modified_time;
    private String avatar_url;

}
