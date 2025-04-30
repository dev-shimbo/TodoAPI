package com.example.api.demo.interceptor;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import com.example.api.demo.service.TokenService;
import com.example.api.demo.service.http.TodoBodyHandleService;

import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * つかってません
 */
@RequiredArgsConstructor
@Component
public class AuthenticationInterceptor implements HandlerInterceptor{

    @Autowired
    TodoBodyHandleService todoBodyHandleService;

    @Autowired
    TokenService tokenService;

    private final String BEARER_PREFIX = "Bearer ";
 
    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler)
            throws Exception {
        // リクエストヘッダーからアクセストークンを取得
        //String accessToken = (String) authentication.getCredentials();
        String accessToken = request.getHeader("Authorization");

        // トークン自体なければ例外で抜ける
        if(accessToken == null){
            todoBodyHandleService.handleExceptionRequest("TOKEN is NULL", HttpStatus.BAD_REQUEST.value());
        }

        if(!isTokenValid(accessToken)){
            todoBodyHandleService.handleExceptionRequest("TOKEN is Expired :" + accessToken.replaceAll(BEARER_PREFIX, ""), HttpStatus.UNAUTHORIZED.value());
        }

        return true;
    }


    private boolean isTokenValid(String accessToken) {

        var tokenList = tokenService.tokenList();

        // トークンデータがなかったら無効扱いで返す
        if(tokenList.isEmpty()){
            return false;
        }

        String rawToken = accessToken.replaceAll(BEARER_PREFIX, "");
      
        for(var token : tokenList){
            if(StringUtils.equals(token.getAccessToken(), rawToken)){
                return true;
            }
        }
        
        return false;
    }
}
