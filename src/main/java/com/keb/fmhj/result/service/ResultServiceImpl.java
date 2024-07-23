package com.keb.fmhj.result.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.response.ApiResponse;
import com.keb.fmhj.result.domain.Result;
import com.keb.fmhj.result.domain.repository.ResultRepository;
import com.keb.fmhj.result.domain.response.ResultDetail;
import com.keb.fmhj.result.domain.response.ResultList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    @Override
    public ApiResponse<?> getResultDetail(Long memberId, Long resultId) {
        //memberId가 없는 경우, resultId가 없는 경우
        Result result = resultRepository.findByMemberIdAndId(memberId, resultId);

        if(result == null){
            return new ApiResponse<>(ErrorCode.INVALID_REQUEST);
        }

        ResultDetail resultDetail = ResultDetail.builder()
                .memberId(memberId)
                .resultId(resultId)
                .faceImage(result.getFaceImage())
                .details(result.getDetails())
                .resultImage(result.getResultImage())
                .build();

        return new ApiResponse<>(resultDetail);
    }

    @Override
    public ApiResponse<?> getResultList(Long memberId) {
        //해당 멤버가 없는 경우 -> 로그인 되어있지 않은 사용자
        List<Result> results = resultRepository.findAllByMemberId(memberId);

        if(results.isEmpty()){
            return new ApiResponse<>(ErrorCode.INVALID_REQUEST);
        }

        List<ResultList> resultList = results.stream().map(result -> {
                    return ResultList.builder()
                            .resultId(result.getId())
                            .resultDate(result.getCreatedAt())
                            .build();
                })
                .toList();
        return new ApiResponse<>(resultList);
    }

}
