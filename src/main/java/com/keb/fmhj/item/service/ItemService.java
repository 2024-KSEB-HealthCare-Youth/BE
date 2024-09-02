package com.keb.fmhj.item.service;

import com.keb.fmhj.item.domain.Category;
import com.keb.fmhj.item.domain.Item;
import com.keb.fmhj.member.domain.dto.request.DiagnosisResult;

import java.util.List;


public interface ItemService {

    // 요청 데이터로부터 Item 저장
    List<Item> createItems(DiagnosisResult diagnosisResult);
    // 아이템 중복 필터링 및 저장
    List<Item> validateAndSaveItems(List<Item> items);
    // 아이템 추출
    List<Item> extractItems(List<String> names, List<String> paths, Category category);
    // 아이템 이름 추출
    List<String> extractItemName(List<Item> items, String category);
    // 아이템 이미지 경로 추출
    List<String> extractItemImgPath(List<Item> items, String category);
}
