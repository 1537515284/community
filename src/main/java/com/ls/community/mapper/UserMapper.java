package com.ls.community.mapper;

import com.ls.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author LS
* @description 针对表【USER】的数据库操作Mapper
* @createDate 2022-05-03 18:42:58
* @Entity com.ls.community.model.User
*/
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("SELECT id, account_id, name, token, gmt_create, gmt_modified FROM user WHERE token = #{token}")
    User findByToken(@Param("token") String token);
}




