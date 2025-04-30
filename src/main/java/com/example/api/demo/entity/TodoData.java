package com.example.api.demo.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Entityクラス
 * JSONPropertyを使うとJSON上では指定した任意のフィールド名にできるx
 */
@Data
public class TodoData {

    /* データID */
    private Integer id;

    /* 書き込み内容 */
    private String todoContext;

    /* 投稿者 */
    private String postUserName;

    /* 作成日 */
    private LocalDateTime createdAt;

    /* 更新日 */
    private LocalDateTime updatedAt;
}
