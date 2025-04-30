package com.example.api.demo.entity.response;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/*
 * リクエストプロパティ
 */
@Data
public class TodoResponseBody {

    @JsonProperty("status")
    private int code;

    /* レスポンスメッセージ */
    private String message;

    /* 書き込み内容 */
    private String todoContext;

    /* 投稿者 */
    private String postUserName;

    /* 作成日 */
    private LocalDateTime createdAt;

    /* 更新日 */
    private LocalDateTime updatedAt;
}
