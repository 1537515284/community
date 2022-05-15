package com.ls.community.controller;

import com.ls.community.dto.PaginationDTO;
import com.ls.community.dto.QuestionDTO;
import com.ls.community.mapper.QuestionMapper;
import com.ls.community.mapper.UserMapper;
import com.ls.community.model.Question;
import com.ls.community.model.User;
import com.ls.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(
            HttpServletRequest request,
            Model model,
            @RequestParam(value = "page",defaultValue = "1") Integer pageNo,
            @RequestParam(value = "size",defaultValue = "5") Integer pageSize
    ){
        PaginationDTO pagination = questionService.list(pageNo,pageSize);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
