package com.ls.community.mapper;

import com.ls.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);

    void incCommentCount(Question question);

    List<Question> selectRelated(Question question);
}
