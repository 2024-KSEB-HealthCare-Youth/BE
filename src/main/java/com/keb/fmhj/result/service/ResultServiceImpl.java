package com.keb.fmhj.result.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.item.domain.Item;
import com.keb.fmhj.item.service.ItemService;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.dto.request.DiagnosisResult;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import com.keb.fmhj.result.domain.AdvancedSkinType;
import com.keb.fmhj.result.domain.Result;
import com.keb.fmhj.result.domain.repository.ResultRepository;
import com.keb.fmhj.result.domain.response.LastResultDetail;
import com.keb.fmhj.result.domain.response.ResultList;
import com.keb.fmhj.result.domain.response.SavedResult;
import com.keb.fmhj.resultItem.domain.ResultItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final MemberRepository memberRepository;
    private final ItemService itemService;

    //내가 진단했던 결과를 날짜별로 목록 조회
    @Override
    @Transactional
    public List<ResultList> getResultList(String loginId) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> YouthException.from(ErrorCode.INVALID_REQUEST));
        List<Result> results = resultRepository.findAllByMemberId(member.getId());

        if (results.isEmpty()) throw YouthException.from(ErrorCode.RESULT_NOT_FOUND);

        List<ResultList> resultList = results.stream().map(result -> {
                    List<AdvancedSkinType> advancedSkinTypes = result.getAdvancedSkinType().stream().toList();
                    return ResultList.builder()
                            .resultId(result.getId())
                            .resultDate(result.getCreatedAt())
                            .advancedSkinTypeList(advancedSkinTypes)
                            .build();
                })
                .toList();

        return resultList;
    }

    //과거 진단 결과 상세 조회
    @Override
    @Transactional
    public LastResultDetail getResultDetail(String loginId, Long resultId) {

        HashMap<String, Double> probabilities = new HashMap<>();
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> YouthException.from(ErrorCode.INVALID_REQUEST));
        Result result = resultRepository.findByMemberIdAndId(member.getId(), resultId);

        //result가 없는 경우
        if (result == null) throw YouthException.from(ErrorCode.RESULT_NOT_FOUND);

        for (Map.Entry<String, Double> probability : result.getProbability().entrySet()) {
            String symptom = probability.getKey();
            Double probabilityValue = probability.getValue();
            probabilities.put(symptom, probabilityValue);
        }

        LastResultDetail resultDetail = LastResultDetail.builder()
                .memberId(member.getId())
                .probabilities(probabilities)
                .faceImage(result.getFaceImage())
                .details(result.getDetails())
                .resultDate(result.getCreatedAt())
                .build();

        return resultDetail;
    }

    //결과 저장
    @Transactional
    @Override
    public SavedResult saveResult(String loginId, DiagnosisResult diagnosisResult) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> YouthException.from(ErrorCode.INVALID_REQUEST));

        //결과 값 넣기
        Result result = Result.builder()
                .member(member)
                .advancedSkinType(Optional.ofNullable(diagnosisResult.getAdvancedSkinType()).orElse(Collections.emptyList()))
                .basicSkinType(diagnosisResult.getBasicSkinType())
                .faceImage(diagnosisResult.getFaceImage())
                .probability(diagnosisResult.getProbabilities())
                .details(diagnosisResult.getResultDetails())
                .resultItems(new ArrayList<>()) // 초기화
                .build();

        //아이템 생성 및 저장
        List<Item> savedItems = itemService.createItems(diagnosisResult);

        //ResultItem
        List<ResultItem> resultItems = savedItems.stream()
                .map(item -> ResultItem.builder()
                        .result(result)
                        .item(item)
                        .build())
                .collect(Collectors.toList());

        result.getResultItems().addAll(resultItems);
        resultRepository.save(result);

        return SavedResult.builder()
                .resultDetails(result.getDetails())
                .advancedSkinType(result.getAdvancedSkinType())
                .basicSkinType(result.getBasicSkinType())
                .probabilities(result.getProbability())
                .build();
    }
}
