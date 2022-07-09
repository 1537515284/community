package com.ls.community.controller;

import com.ls.community.dto.CommentDTO;
import com.ls.community.dto.QuestionDTO;
import com.ls.community.enums.CommentTypeEnum;
import com.ls.community.exception.CustomizeErrorCode;
import com.ls.community.exception.CustomizeException;
import com.ls.community.service.CommentService;
import com.ls.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(
            @PathVariable("id") String id,
            Model model){
        Long questionId = null;
        try{
            questionId = Long.parseLong(id);
        }catch (NumberFormatException e){
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }
        QuestionDTO questionDTO = questionService.findById(questionId);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(questionId, CommentTypeEnum.QUESTION);
        // 增加阅读数
        questionService.incView(questionId);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
