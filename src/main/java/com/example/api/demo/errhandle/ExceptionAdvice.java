package com.example.api.demo.errhandle;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.api.demo.entity.response.ErrorResponseBody;
import com.example.api.demo.exception.ResponseStatusException;

/**
 * 例外ハンドリングクラス
 */
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    /**
     * 例外発生時に呼ばれる
     * 
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        return super.handleExceptionInternal(exception,
                createErrorResponseBody(exception, request),
                headers,
                HttpStatus.BAD_REQUEST,
                request);
    }

    // レスポンスのボディ部を作成
    private ErrorResponseBody createErrorResponseBody(ResponseStatusException exception, WebRequest request) {

        var errorResponseBody = new ErrorResponseBody();

        int statusCode = exception.getResponseBody().getStatus();

        String responseErrorMessage = handleErrMessage(statusCode);

        String uri = ((ServletWebRequest) request).getRequest().getRequestURI();

        errorResponseBody.setStatus(statusCode);
        errorResponseBody.setExceptionOccurrenceTime(LocalDateTime.now());
        errorResponseBody.setError(responseErrorMessage);
        errorResponseBody.setMessage(exception.getMessage());
        errorResponseBody.setPath(uri);
        errorResponseBody.setErrorDetail(exception.getResponseBody().getMessage());

        return errorResponseBody;
    }

    /**
     * ステータスコード別にデフォルトフレーズも変化させる
     * 
     * @param statusCode
     * @return
     */
    private String handleErrMessage(int statusCode) {
        // default 400 にする
        String message = HttpStatus.BAD_REQUEST.getReasonPhrase();

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            message = HttpStatus.NOT_FOUND.getReasonPhrase();

        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();

        } else if (statusCode == HttpStatus.CONFLICT.value()) {
            message = HttpStatus.CONFLICT.getReasonPhrase();

        } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            message = HttpStatus.UNAUTHORIZED.getReasonPhrase();

        }
        return message;
    }
}
