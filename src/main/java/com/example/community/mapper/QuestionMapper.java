package com.example.community.mapper;

import com.example.community.model.Question;
import com.example.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {
    @Insert("insert into question (title,content,creator_uid,creat_time,modified_time,tags) values (#{title},#{content},#{creator_uid},#{creat_time},#{modified_time},#{tags})")
    void insert(Question question);

    @Update("update question set title=#{title},content=#{content},tags=#{tags},modified_time=#{modified_time} where id=#{id}")
    void update(Question question);

    @Select("select * from question")
    List<Question> list();

    @Select("select * from question where tags regexp #{tags} and id != #{id} ORDER BY creat_time DESC limit 10")
    List<Question> findRelatedByTags(@Param("tags") String tags,@Param("id") Integer id);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where title regexp #{search}")
    Integer countBySearch(String search);

    @Select("select * from question where creator_uid = #{creator_uid}")
    List<Question> findByUid(Integer creator_uid);

    @Select("select count(1) from question where creator_uid = #{creator_uid}")
    Integer countByUid(Integer creator_uid);

    @Select("select * from question where creator_uid = #{creator_uid} ORDER BY creat_time DESC limit #{offset},#{size}")
    List<Question> findByUid_limit(@Param("creator_uid") Integer creator_uid, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from question ORDER BY creat_time DESC limit #{offset},#{size}")
    List<Question> list_limit(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from question where id = #{id}")
    Question findById(Integer id);

    @Update("update question set view_count = view_count+1 where id = #{id}")
    void addView_count(Integer id);

    @Update("update question set like_count = like_count+1 where id = #{id}")
    void addLike_count(Integer id);

    @Update("update question set comment_count = comment_count+1 where id = #{id}")
    void addComment_count(Integer id);

    @Select("select * from question where title regexp #{search} ORDER BY creat_time DESC limit #{offset},#{size}")
    List<Question> listBySearch_limit(@Param("search") String search,@Param("offset") Integer offset,@Param("size") Integer size);
}
