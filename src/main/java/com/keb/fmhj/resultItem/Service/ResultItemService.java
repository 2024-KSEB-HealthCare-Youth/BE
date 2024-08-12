package com.keb.fmhj.resultItem.Service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.item.domain.Item;
import com.keb.fmhj.item.domain.repository.ItemRepository;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import com.keb.fmhj.result.domain.Result;
import com.keb.fmhj.result.domain.repository.ResultRepository;
import com.keb.fmhj.resultItem.domain.dto.response.RecommendData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultItemService {

    private final ResultRepository resultRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public RecommendData getResultItems(String loginId) {
        // 1. Member를 가져오고, 그로부터 최근 Result를 가져옵니다.
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> YouthException.from(ErrorCode.INVALID_REQUEST));

        Result recentResult = resultRepository.findLatestResultByMemberId(member.getId());

        // 2. 최근 Result의 ID로 ResultItem 리스트 가져오기
        List<Item> recommendItems = itemRepository.findItemsByResultId(recentResult.getId());

        // 3. Item 정보를 추출하여 각 리스트로 변환
        List<String> cosNames = extractItemName(recommendItems, "COSMETIC");
        List<String> cosPaths = extractItemImgPath(recommendItems, "COSMETIC");
        List<String> nutrNames = extractItemName(recommendItems, "NUTRIENT");
        List<String> nutrPaths = extractItemImgPath(recommendItems, "NUTRIENT");

        // 4. RecommendData 생성 및 반환
        return RecommendData.builder()
                .name(member.getName())
                .basicSkinType(recentResult.getBasicSkinType())
                .advancedSkinType(recentResult.getAdvancedSkinType().stream().toList())
                .cosNames(cosNames)
                .cosPaths(cosPaths)
                .nutrNames(nutrNames)
                .nutrPaths(nutrPaths)
                .build();
    }

    private List<String> extractItemName(List<Item> items, String category){

        List<String> recommendItemNames = items.stream()
                .filter(item->item.getCategory().toString().equals(category))
                .map(Item::getName)
                .collect(Collectors.toList());

        return recommendItemNames;
    }

    private List<String> extractItemImgPath(List<Item> items, String category){

        List<String> recommendItemNames = items.stream()
                .filter(item->item.getCategory().toString().equals(category))
                .map(Item::getName)
                .collect(Collectors.toList());

        return recommendItemNames;
    }
}
