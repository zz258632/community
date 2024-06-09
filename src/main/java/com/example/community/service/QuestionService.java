package com.example.community.service;

import com.example.community.dto.QuestionDTO;
import com.example.community.exception.myException;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public static Integer size=7;

    public QuestionDTO question2dto(Question question){
        User user=userMapper.findByUid(question.getCreator_uid());
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setCreat_time(Long.parseLong(question.getCreat_time()));
        questionDTO.setModified_time(Long.parseLong(question.getModified_time()));
        questionDTO.setUser(user);
        return questionDTO;
    }

    public QuestionDTO question2dto(Question question,User user){
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setCreat_time(Long.parseLong(question.getCreat_time()));
        questionDTO.setModified_time(Long.parseLong(question.getModified_time()));
        questionDTO.setUser(user);
        return questionDTO;
    }

    public List<QuestionDTO> list(Integer page){
        Integer offset=size*(page-1);
        List<Question> questions=questionMapper.list_limit(offset,size);
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        for(Question question:questions){
            questionDTOS.add(question2dto(question));
        }
        return questionDTOS;
    }

    public List<QuestionDTO> listBySearch(String search, Integer page) {
        Integer offset=size*(page-1);
        search = search.trim();
        List<Question> questions=questionMapper.listBySearch_limit(search,offset,size);
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        for(Question question:questions){
            questionDTOS.add(question2dto(question));
        }
        return questionDTOS;
    }

    public List<QuestionDTO> myQuestions(String account,Integer page){
        Integer offset=size*(page-1);
        User user=userMapper.findByAccount(account);
        List<Question> questions=questionMapper.findByUid_limit(user.getUid(),offset,size);
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        for(Question question:questions){
            questionDTOS.add(question2dto(question,user));
        }
        return questionDTOS;
    }

    public QuestionDTO findById(Integer id) {
        Question question=questionMapper.findById(id);
        if(question==null){
            throw new myException("问题不存在或已被删除");
        }
        return question2dto(question);
    }


}
