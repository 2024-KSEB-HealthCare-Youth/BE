package com.keb.fmhj.item.service;

import com.keb.fmhj.item.domain.Category;
import com.keb.fmhj.item.domain.Item;
import com.keb.fmhj.item.domain.repository.ItemRepository;
import com.keb.fmhj.member.domain.dto.request.DiagnosisResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor

public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    // 요청 데이터로부터 Item 저장
    @Transactional
    @Override
    public List<Item> createItems(DiagnosisResult diagnosisResult) {

        //화장품
        List<Item> cosmetics = extractItems(diagnosisResult.getCosNames(), diagnosisResult.getCosPaths(), Category.COSMETIC);

        //영양제
        List<Item> nutrients = extractItems(diagnosisResult.getNutrNames(), diagnosisResult.getNutrPaths(), Category.NUTRIENT);

        //제품 통합
        List<Item> integrateItems = new ArrayList<>();
        integrateItems.addAll(cosmetics);
        integrateItems.addAll(nutrients);

        //제품 가져오기
        List<Item> returnItems = validateAndSaveItems(integrateItems);
        return returnItems;
    }

    // 제품 중복 필터링 및 저장
    @Transactional
    @Override
    public List<Item> validateAndSaveItems(List<Item> items) {

        // 입력받은 아이템 이름 리스트 추출
        List<String> names = items.stream().map(Item::getName).collect(Collectors.toList());

        // 중복되는 제품
        List<Item> duplicateItems = itemRepository.findItemsByNamesExist(names);
        Set<String> duplicateNames = duplicateItems.stream().map(Item::getName).collect(Collectors.toSet());

        // 중복되지 않는 제품
        List<Item> notDuplicateItems = items.stream().filter(item -> !duplicateNames.contains(item.getName())).collect(Collectors.toList());

        // 테이블에 존재하지 않는 제품이 있다면, 저장
        if(!notDuplicateItems.isEmpty()) itemRepository.saveAll(notDuplicateItems);

        // 중복 데이터와 그렇지 않은 데이터 통합
        List<Item> allItems = new ArrayList<>();
        allItems.addAll(duplicateItems);
        allItems.addAll(notDuplicateItems);

        return allItems;
    }

    // 제품 데이터 추출
    @Transactional
    @Override
    public List<Item> extractItems(List<String> names, List<String> paths, Category category) {

        return IntStream.range(0, names.size())
                .mapToObj(i -> Item.builder()
                        .name(names.get(i))
                        .itemImage(paths.get(i))
                        .category(category)
                        .build())
                .collect(Collectors.toList());
    }

    public List<String> extractItemName(List<Item> items, String category){

        List<String> recommendItemNames = items.stream()
                .filter(item->item.getCategory().toString().equals(category))
                .map(Item::getName)
                .collect(Collectors.toList());

        return recommendItemNames;
    }

    public List<String> extractItemImgPath(List<Item> items, String category){

        List<String> recommendItemNames = items.stream()
                .filter(item->item.getCategory().toString().equals(category))
                .map(Item::getItemImage)
                .collect(Collectors.toList());

        return recommendItemNames;
    }
}
