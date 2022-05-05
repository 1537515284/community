package com.ls.community.service;

import com.ls.community.dto.PaginationDTO;
import com.ls.community.dto.QuestionDTO;
import com.ls.community.mapper.QuestionMapper;
import com.ls.community.mapper.UserMapper;
import com.ls.community.model.Question;
import com.ls.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer pageSize) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,pageSize);

        if(page < 1)
            page = 1;
        if(page > paginationDTO.getTotalPage())
            page = paginationDTO.getTotalPage();

        Integer offset = pageSize *(page-1);

        List<Question> questionList = questionMapper.list(offset,pageSize);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList) {
             User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);




        return paginationDTO;
    }
}
