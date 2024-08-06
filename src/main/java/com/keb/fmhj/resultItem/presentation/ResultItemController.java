package com.keb.fmhj.resultItem.presentation;

import com.keb.fmhj.auth.utils.AccessTokenUtils;
import com.keb.fmhj.global.response.ApiResponse;
import com.keb.fmhj.resultItem.Service.ResultItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ResultItemController {

    private final AccessTokenUtils accessTokenUtils;
    private final ResultItemService resultItemService;

    @GetMapping("/items")
    @Operation(summary = "추천 제품 화면 조회 API", description = "추천 제품들을 조회합니다.")
    public ApiResponse<?> getRecommendItems(){

        return new ApiResponse<>(resultItemService.getResultItems(accessTokenUtils.isPermission()));
    }

}
