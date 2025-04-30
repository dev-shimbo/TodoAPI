package com.example.api.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.entity.AccessToken;
import com.example.api.demo.entity.TodoData;
import com.example.api.demo.entity.response.TodoResponseBody;
import com.example.api.demo.entity.response.TokenResponse;
import com.example.api.demo.messages.TodoAPIMessages;
import com.example.api.demo.service.TodoService;
import com.example.api.demo.service.TokenService;
import com.example.api.demo.service.http.TodoBodyHandleService;
import com.example.api.demo.util.StringUtil;

import lombok.RequiredArgsConstructor;

/**
 * TODO情報のCRUD API コントローラークラス
 */
@CrossOrigin
@RequiredArgsConstructor
@RestController
public class TodoApiController {

    /* TODOデータ サービスクラス */
    final TodoService todoService;

    /* レスポンス管理サービスクラス */
    final TodoBodyHandleService requestHandler;

    /* 認証トークン管理サービス */
    final TokenService tokenService;

    /**
     * 一覧
     *
     * @return TodoDataのリスト(json形式)
     */
    @GetMapping("/api/todo/list")
    public List<TodoData> index() {

        var list = todoService.list();
        return list;
    }

    /**
     * 選択
     *
     * @return TodoData(json形式)
     */
    @GetMapping("/api/todo/{id}")
    public TodoData selectTodo(@PathVariable("id") Integer id) {

        var resultById = todoService.select(id);

        if (resultById == null) {
            requestHandler.handleExceptionRequest(TodoAPIMessages.DATA_NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }
        return resultById;
    }

    /**
     * id別選択例外
     *
     * @return
     */
    @GetMapping("/api/todo/")
    public void handleSelectTodo() {
        requestHandler.handleExceptionRequest(TodoAPIMessages.NO_PARAMETER, HttpStatus.BAD_REQUEST.value());
    }

    /**
     * accessTokenを生成する
     * トークン認証を実装したいので
     * 今回はユーザーネームを適当に指定したらトークンが返るように
     * トークンは有効期限付き
     *
     */
    @GetMapping("/api/token/generate/{username}")
    @ResponseBody
    public TokenResponse generateAccessToken(@PathVariable String username) {

        // 今回はトークンはひとり一つまでなので、無効化されない限りトークンの複製は禁止する
        // 名前変えたらいくらでも作れるので、なんとも言えないとこですが
        if (tokenService.isYourTokenAlreadyExists(username)) {
            requestHandler.handleExceptionRequest("あなたのトークンはすでに存在します。", HttpStatus.CONFLICT.value());
        }
        var tokenObj = new AccessToken();
        tokenObj.setAccessToken(StringUtil.generateAccessTokenByUserName(username));
        tokenObj.setCreatedAt(LocalDateTime.now());

        tokenService.tokenSaveToDB(tokenObj);

        var tokenResponse = tokenService.createTokenResponse(tokenObj);

        return tokenResponse;
    }

    @GetMapping("/api/tokens")
    @ResponseBody
    public List<AccessToken> viewToken() {
        return tokenService.tokenList();
    }

    /**
     * 新規作成
     * リクエストURL例:(Windowsのcurlはシングルクオートが使えない。そのうえエスケープ処理も必要)
     * curl -X POST -H "Content-Type:application/json" -d
     * "{\"todo_context\":\"バグを直す\",\"post_user_name\": \"Hello-san\"}"
     * http://localhost:8080/api/todo
     * TOKENがある場合(仮)
     * curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer
     * <TOKEN>" -d "{\"todo_context\":\"バグを直す\",\"post_user_name\": \"Hello-san\"}"
     * http://localhost:8080/api/todo
     *
     * @param entity
     * @return 処理完了レスポンスエンティティ(JSON)
     */
    @PostMapping("api/todo")
    @ResponseBody
    public TodoResponseBody create(@RequestBody @Validated TodoData entity, BindingResult result) {

        // 不適切なRequestJSONが送信されたらキャッチ
        if (result.hasErrors()) {
            requestHandler.handleExceptionRequest(TodoAPIMessages.WRONG_REQUEST, HttpStatus.BAD_REQUEST.value());
        }

        todoService.create(entity);

        var postResponse = requestHandler.createTodoResponseBody(HttpStatus.CREATED.value(), entity, "POST TODO");

        return postResponse;
    }

    /**
     * 編集
     *
     * @param entity
     * @param result
     * @return
     */
    @PutMapping("api/todo")
    @ResponseBody
    public TodoResponseBody update(@RequestBody @Validated TodoData entity, BindingResult result) {

        var resultById = todoService.select(entity.getId());
        // 存在しないデータを編集しようとしてたらエラー
        if (resultById == null) {
            requestHandler.handleExceptionRequest(TodoAPIMessages.DATA_NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }
        // 不適切なRequestJSONが送信されたらキャッチ
        if (result.hasErrors()) {
            // result.getFieldError();
            requestHandler.handleExceptionRequest(TodoAPIMessages.WRONG_REQUEST, HttpStatus.BAD_REQUEST.value());
        }

        todoService.update(entity);
        // レスポンスでは、編集前データも表示する
        var updatedInfoEntity = todoService.getEditResultEntity(resultById, entity);
        var postResponse = requestHandler.createTodoResponseBody(HttpStatus.CREATED.value(), updatedInfoEntity, "UPDATE TODO");;

        return postResponse;
    }

    /**
     * 削除
     *
     * @param id
     * @return 処理完了レスポンスエンティティ(JSON)
     */
    @DeleteMapping("/api/todo/{id}")
    public TodoResponseBody delete(@PathVariable("id") Integer id) {

        var resultById = todoService.select(id);

        if (resultById == null) {
            requestHandler.handleExceptionRequest(TodoAPIMessages.DATA_NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }

        todoService.delete(id);

        var deleteResponse = requestHandler.createTodoResponseBody(HttpStatus.OK.value(), resultById, "DELETE TODO");

        return deleteResponse;
    }
}