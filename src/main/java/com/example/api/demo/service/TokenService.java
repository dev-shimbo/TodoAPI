package com.example.api.demo.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.api.demo.entity.AccessToken;
import com.example.api.demo.entity.response.TokenResponse;
import com.example.api.demo.mapper.token.TokenMapper;
import com.example.api.demo.util.StringUtil;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Component
@Service
public class TokenService {

    final TokenMapper tokenMapper;

    public List<AccessToken> tokenList() {

        var list = tokenMapper.getTokensList();
        return list;
    }

    public void tokenSaveToDB(AccessToken tokenEntity) {
        tokenMapper.create(tokenEntity);
    }

    /**
     * token用のレスポンスを作成
     * 
     * @param tokenEntity
     * @return
     */
    public TokenResponse createTokenResponse(AccessToken tokenEntity) {
        var tokenCreatedAt = tokenEntity.getCreatedAt();
        var responseEntity = new TokenResponse();
        // 期限 10分なので+10分で設定
        var tokenExpiredAt = tokenCreatedAt.plus(10, ChronoUnit.MINUTES);

        responseEntity.setAccessToken(tokenEntity.getAccessToken());
        responseEntity.setCreatedAt(tokenCreatedAt);
        responseEntity.setExpiredAt(tokenExpiredAt);

        return responseEntity;
    }

    // トークン生成から10分後にトークンを削除する
    @Scheduled(initialDelay = 0, fixedDelay = 60000) // 30秒ごとに実行
    public void tokenDeleteGeneratedTenMinLater() {

        final int TOKEN_EXPIRED_TIME_FROM_CREATED = 10;

        Logger logger = LoggerFactory.getLogger(TokenService.class);

        var list = tokenMapper.getTokensList();
        var currentTime = LocalDateTime.now();

        logger.info("checkin invaild tokens now");

        for (var token : list) {
            var tokenCreatedAt = token.getCreatedAt();
            // 2つの日時の差を計算
            Duration duration = Duration.between(tokenCreatedAt, currentTime);
            // 分単位での差を取得
            long minDurationFromTokenCreated = duration.toMinutes();

            // 現在時間とトークン作成時間の差が指定時間になったら削除を実行
            if (minDurationFromTokenCreated >= TOKEN_EXPIRED_TIME_FROM_CREATED) {
                tokenMapper.delete(token.getTokenId());
                logger.info("token expired id:" + token.getTokenId());
            }
        }
    }

    // トークンに自分の名前をハッシュ化した部分が存在すれば作成済みとする
    public boolean isYourTokenAlreadyExists(String username) {
        String hashedUserName = StringUtil.hashSHA256(username);
        var tokenList = tokenMapper.getTokensList();

        for (var token : tokenList) {
            if (token.getAccessToken().contains(hashedUserName)) {
                return true;
            }
        }
        return false;
    }
}
