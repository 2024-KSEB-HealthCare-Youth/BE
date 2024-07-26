package com.keb.fmhj.result.service;

import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import com.keb.fmhj.result.domain.Result;
import com.keb.fmhj.result.domain.repository.ResultRepository;
import com.keb.fmhj.result.domain.response.LastResultDetail;
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
    private final MemberRepository memberRepository;

    //내가 진단했던 목록을 날짜별로 조회
    @Override
    public List<ResultList> getResultList() {
        Member member = memberRepository.findById(1l).orElseThrow();
        //해당 멤버가 없는 경우 -> 로그인 되어있지 않은 사용자
        List<Result> results = resultRepository.findAllByMemberId(member.getId());
/*
        if(results.isEmpty()){
            return new ApiResponse<>(ErrorCode.INVALID_REQUEST);
        }*/

        List<ResultList> resultList = results.stream().map(result -> {
                    return ResultList.builder()
                            .resultId(result.getId())
                            .resultDate(result.getCreatedAt())
                            .build();
                })
                .toList();

        return resultList;
    }

    //과거 진단 결과 조회
    @Override
    public LastResultDetail getResultDetail(Long resultId) {

        Member member = memberRepository.findById(1l).orElseThrow();

        //memberId가 없는 경우, resultId가 없는 경우
        Result result = resultRepository.findByMemberIdAndId(member.getId(), resultId);

        if(result == null){
            // return new ApiResponse<>(ErrorCode.INVALID_REQUEST);
        }

        LastResultDetail resultDetail = LastResultDetail.builder()
                .resultId(resultId)
                .memberId(member.getId())
                .resultImage(result.getResultImage())
                .faceImage(result.getFaceImage())
                .details(result.getDetails())
                .resultDate(result.getCreatedAt())
                .build();

        return resultDetail;
    }

}
