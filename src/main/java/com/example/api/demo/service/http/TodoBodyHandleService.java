package com.example.api.demo.service.http;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.example.api.demo.entity.response.ErrorResponseBody;
import com.example.api.demo.entity.response.TodoResponseBody;
import com.example.api.demo.exception.ResponseStatusException;
import com.example.api.demo.entity.TodoData;

@Service
public class TodoBodyHandleService {
    /**
     * リクエスト自体をハンドルする
     * 
     * @param statusCode
     * @param entity
     * @return
     */
    public TodoResponseBody createTodoResponseBody(int statusCode, TodoData entity, String requestType) {
        var res = new TodoResponseBody();

        res.setMessage("TODO-INFO(SUCCESS):" + requestType);
        res.setCode(statusCode);
        res.setTodoContext(entity.getTodoContext());
        res.setPostUserName(entity.getPostUserName());

        res.setCreatedAt(LocalDateTime.now());
        res.setUpdatedAt(LocalDateTime.now());

        if(StringUtils.equals(requestType, "UPDATE TODO")){
            res.setCreatedAt(entity.getCreatedAt());
        }
        return res;
    }

    /* 例外発生時処理はErrResponseBodyに任せる */
    /**
     * 独自例外的リクエストが来た際に独自例外を投げる。
     * 
     * 
     * @return
     * @throws ResponseStatusException
     */
    public void handleExceptionRequest(String errBodyMessage, int status) throws ResponseStatusException {
        var errBody = new ErrorResponseBody();
        errBody.setStatus(status);
        errBody.setMessage(errBodyMessage);

        throw new ResponseStatusException("不正なリクエストが発生しました。", errBody);
    }
}
