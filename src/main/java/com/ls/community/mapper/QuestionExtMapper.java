package com.ls.community.mapper;

import com.ls.community.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);

    void incCommentCount(Question question);
}
