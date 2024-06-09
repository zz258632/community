package com.example.community.service;

import com.example.community.dto.ChildComment;
import com.example.community.dto.CommentDTO;
import com.example.community.mapper.CommentMapper;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Comment;
import com.example.community.model.Question;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public List<ChildComment> comments2childComments(List<Comment> comments){
        List<ChildComment> childComments=new ArrayList<>();
        for (Comment comment : comments) {
            User user=userMapper.findByUid(comment.getCommentator_uid());
            ChildComment childComment=new ChildComment();
            BeanUtils.copyProperties(comment,childComment);
            childComment.setUser(user);
            childComments.add(childComment);
        }
        return childComments;
    }

    public List<CommentDTO> findByQuestionId(Integer id) {
        List<Comment> comments=commentMapper.findByQuestionId(id);
        List<CommentDTO> commentDTOS=new ArrayList<>();
        for (Comment comment:comments) {
            User user=userMapper.findByUid(comment.getCommentator_uid());
            List<ChildComment> childComments=comments2childComments(commentMapper.findChildByCommentId(comment.getId()));
            CommentDTO commentDTO=new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(user);
            commentDTO.setChildComments(childComments);
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;
    }
}
