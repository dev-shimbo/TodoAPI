package com.example.api.demo.mapper.token;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.api.demo.entity.AccessToken;


@Mapper
public interface TokenMapper {

    @Insert("INSERT INTO api.token(access_token, created_at) VALUES (#{accessToken}, now())")
    void create(AccessToken tokenEntity);
 

    @Select("SELECT * FROM api.token")
    List<AccessToken> getTokensList();


    @Delete("DELETE FROM api.token WHERE token_id =#{id}")
    void delete(@Param("id") Integer id);
}
