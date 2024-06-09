package com.example.community.mapper;

import com.example.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from user where account = #{account}")
    User findByAccount(String account);

    @Select("select * from user where uid = #{uid}")
    User findByUid(Integer uid);

    @Insert("insert into user (account,password,creat_time,modified_time,avatar_url) values (#{account},#{password},#{creat_time},#{modified_time},#{avatar_url})")
     void insert(User user);
}
