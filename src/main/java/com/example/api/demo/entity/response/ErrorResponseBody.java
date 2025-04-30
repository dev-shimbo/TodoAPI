package com.example.api.demo.entity.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


/**
 * エラーレスポンスクラス
 * ここをカスタムすれば
 * 任意の形式のレスポンスを作成できる
 */
@Data
public class ErrorResponseBody {
    /* エラー発生時間 */
    @JsonProperty("timestamp")
    private LocalDateTime exceptionOccurrenceTime;

    /* HTTP ERROR CODE */
    private int status;

    /* Http エラーメッセージ */
    private String error;

    /* 例外概要 */
    private String message;

    /* リクエストURL */
    private String path;

    /* エラー発生時間 */
    private String errorDetail;
}
