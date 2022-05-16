package com.ls.community.mapper;

import com.ls.community.model.Question;
import org.apache.ibatis.annotations.*;

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

    @Select("SELECT * FROM question LIMIT #{offset},#{pageSize}")
    List<Question> list(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    @Select("SELECT COUNT(*) FROM question")
    Integer count();

    @Select("SELECT * FROM question WHERE creator = #{userId} LIMIT #{offset},#{pageSize}")
    List<Question> listByUserId(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    @Select("SELECT COUNT(*) FROM question WHERE creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("SELECT * FROM question WHERE id = #{id}")
    Question findById(Integer id);

    @Update("UPDATE question SET title = #{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} WHERE id = #{id}")
    void update(Question question);
}




