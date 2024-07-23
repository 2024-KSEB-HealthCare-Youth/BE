package com.keb.fmhj.result.service;
import com.keb.fmhj.global.response.ApiResponse;

public interface ResultService {
    ApiResponse<?> getResultDetail(Long memberId, Long resultId);
    ApiResponse<?> getResultList(Long memberId);
}
