package com.example.api.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.api.demo.entity.TodoData;

@Mapper
public interface TodoMapper {

    @Select("SELECT * FROM api.todo")
    List<TodoData> selectAll();

    @Select("SELECT * FROM api.todo WHERE id = #{id}")
    TodoData findById(@Param("id") Integer id);
    
    @Insert("INSERT INTO api.todo(todo_context, post_user_name, created_at, updated_at) VALUES (#{todoContext},#{postUserName}, now(), now())")
    Integer create(TodoData entity);

    @Update("UPDATE api.todo SET (todo_context, updated_at) = (#{todoContext}, now()) WHERE id = #{id}")
    Integer update(TodoData entity);

    @Delete("DELETE FROM api.todo WHERE id = #{id}")
    Integer delete(@Param("id") Integer id);
}
