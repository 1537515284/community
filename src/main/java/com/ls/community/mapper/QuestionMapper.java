package com.ls.community.mapper;

import com.ls.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author LS
* @description 针对表【QUESTION】的数据库操作Mapper
* @createDate 2022-05-04 21:40:35
* @Entity com.ls.community.model.Question
*/
@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question(title,description,gmt_create,gmt_modified,creator,tag) VALUES(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("SELECT * FROM question")
    List<Question> list();
}




