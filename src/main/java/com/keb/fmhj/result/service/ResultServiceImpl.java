package com.keb.fmhj.result.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
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
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final MemberRepository memberRepository;

    //내가 진단했던 목록을 날짜별로 조회
    @Override
    @Transactional
    public List<ResultList> getResultList(String loginId) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> YouthException.from(ErrorCode.INVALID_REQUEST));
        List<Result> results = resultRepository.findAllByMemberId(member.getId());

        if (results.isEmpty()) throw YouthException.from(ErrorCode.RESULT_NOT_FOUND);

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
    @Transactional
    public LastResultDetail getResultDetail(String loginId, Long resultId) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> YouthException.from(ErrorCode.INVALID_REQUEST));
        Result result = resultRepository.findByMemberIdAndId(member.getId(), resultId);

        //result가 없는 경우
        if (result == null) throw YouthException.from(ErrorCode.RESULT_NOT_FOUND);

        LastResultDetail resultDetail = LastResultDetail.builder()
                .memberId(member.getId())
                .probabilities(result.getProbability())
                .faceImage(result.getFaceImage())
                .details(result.getDetails())
                .resultDate(result.getCreatedAt())
                .build();

        return resultDetail;
    }

}
