package com.example.api.demo.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * アクセストークンオブジェクト
 * 本来はユーザーベースでプロバイダからもらったりするが
 * 簡易的にトークンのみとする
 * 
 */
@Data
public class AccessToken {
    /* トークンID */
    @JsonProperty("token_id")
    private Integer tokenId;

    /* アクセストークン */
    @JsonProperty("access_token")
    private String accessToken;

    /* トークン作成日時 */
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
