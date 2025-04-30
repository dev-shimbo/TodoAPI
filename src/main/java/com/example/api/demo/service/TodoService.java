package com.example.api.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api.demo.entity.TodoData;
import com.example.api.demo.mapper.TodoMapper;

import lombok.RequiredArgsConstructor;

/**
 * TODOAPI サービスクラス
 */
@RequiredArgsConstructor
@Service
public class TodoService {

    /* TODOデータ取得 Mapperクラス(Mybatis3使用) */
    final TodoMapper todoMapper;

    /**
     * TODOデータ一覧取得
     * 
     * @return TODOリストデータ
     */
    public List<TodoData> list() {
        return todoMapper.selectAll();
    }

    /**
     * TODOデータ取得(id)
     * 
     * @param id TODOid
     * @return  TODOデータ
     */
    public TodoData select(Integer id){
        return todoMapper.findById(id);
    }

    /**
     * TODOデータ一作成
     * 
     * @param entity TODOデータ (リクエストボディ)
     */
    public void create(TodoData entity) {
        todoMapper.create(entity);
    }

    /**
     * TODOデータ削除
     * 
     * @param id
     * 
     */
    public void delete(Integer id){
        todoMapper.delete(id);
    }

    /**
     * TODOデータ編集
     * 
     * @param entity
     */
    public void update(TodoData entity){
        todoMapper.update(entity);
    }

    /**
     * TODOデータ編集結果を格納したEntityを作る
     */
    public TodoData getEditResultEntity(TodoData before, TodoData after){
        String beforeTodo = before.getTodoContext();
        String afterTodo = after.getTodoContext();
        String postUserName = before.getPostUserName();
        
        after.setTodoContext("before:" + beforeTodo + ", current:" + afterTodo);
        after.setPostUserName(postUserName);
        return after;
    }
}
