package com.example.api.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.api.demo.interceptor.AuthenticationInterceptor;

// インターセプト処理の登録
@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

    // Beanにして登録できるようにする
    @Bean
    AuthenticationInterceptor authInterCeptor() {
        return new AuthenticationInterceptor();
    }

    /**
     * 登録と削除だけインターセプトをかける
     * パス変数こっちでも指定しないとそのまますり抜けてしまうっぽい
     */
    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    //     registry.addInterceptor(authInterCeptor()).addPathPatterns(
    //             "/api/todo/create", "/api/todo/delete/{id}");
    // }
}
