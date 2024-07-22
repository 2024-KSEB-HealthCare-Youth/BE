package com.keb.fmhj.global.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class ApiResponse<T> {
    private Status status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Metadata metadata; // 결과 개수

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<T> results;

    // 단일 결과 반환
    public ApiResponse(T data) {
        this.status = new Status(ErrorCode.REQUEST_OK);
        this.metadata = new Metadata(1);
        this.results = List.of(data);
    }

    // 여러 개의 결과 반환
    public ApiResponse(List<T> results) {
        this.status = new Status(ErrorCode.REQUEST_OK);
        this.metadata = new Metadata(results.size());
        this.results = results;
    }


    public ApiResponse(ErrorCode errorCode) {
        this.status = new Status(errorCode);
    }

    public ApiResponse(YouthException exception) {
        this.status = new Status(exception.getErrorCode());
    }

    @Getter
    @AllArgsConstructor
    private static class Metadata {
        private int resultCount = 0;
    }

    @Getter
    private static class Status {
        private int code;
        private String message;

        public Status(ErrorCode errorCode) {
            this.code = errorCode.getStatus().value();
            this.message = errorCode.getMessage();
        }
    }
}