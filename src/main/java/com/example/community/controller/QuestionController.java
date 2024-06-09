package com.example.community.controller;

import com.example.community.dto.CommentCreatDTO;
import com.example.community.dto.CommentDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.CommentMapper;
import com.example.community.mapper.QuestionMapper;
import com.example.community.model.Question;
import com.example.community.service.CommentService;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model) {
        QuestionDTO questionDTO = questionService.findById(id);
        List<CommentDTO> comments = commentService.findByQuestionId(id);
        List<Question> relatedQuestions = questionMapper.findRelatedByTags(questionDTO.getTags(), id);
        questionMapper.addView_count(id);
        questionDTO.setView_count(questionDTO.getView_count() + 1);

        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }

    @ResponseBody
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    public void post(HttpServletRequest request,
                     @RequestBody Map<String, Object> requestBody) {
        questionMapper.addLike_count((Integer) requestBody.get("id"));
    }
}
