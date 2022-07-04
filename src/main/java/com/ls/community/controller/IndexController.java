package com.ls.community.controller;

import com.ls.community.cache.HotTagCache;
import com.ls.community.dto.HotTagDTO;
import com.ls.community.dto.PaginationDTO;
import com.ls.community.mapper.UserMapper;
import com.ls.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")
    public String index(
            HttpServletRequest request,
            Model model,
            @RequestParam(value = "page",defaultValue = "1") Integer pageNo,
            @RequestParam(value = "size",defaultValue = "5") Integer pageSize,
            @RequestParam(value = "search",required = false) String search,
            @RequestParam(value = "tag",required = false) String tag
    ){
        PaginationDTO pagination = questionService.list(search,tag,pageNo,pageSize);
        List<String > tags = hotTagCache.getHots();
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        model.addAttribute("tag",tag);
        model.addAttribute("tags",tags);
        return "index";
    }
}
