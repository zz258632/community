package com.example.community.mapper;

import com.example.community.model.Comment;
import com.example.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    @Insert("insert into comment (parent_id,isChildComment,content,commentator_uid,creat_time,modified_time) values (#{parent_id},#{isChildComment},#{content},#{commentator_uid},#{creat_time},#{modified_time})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id=#{parent_id} and isChildComment=0 ORDER BY creat_time DESC;")
    List<Comment> findByQuestionId(Integer parent_id);

    @Select("select * from comment where parent_id=#{parent_id} and isChildComment=1 ORDER BY creat_time DESC;")
    List<Comment> findChildByCommentId(Integer parent_id);

    @Select("select * from comment where id=#{id}")
    Comment findById(Integer id);

    @Update("update comment set like_count = like_count+1 where id = #{id}")
    void addLike_count(Integer id);
}
