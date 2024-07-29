package com.keb.fmhj.result.presentation;


import com.keb.fmhj.global.response.ApiResponse;
import com.keb.fmhj.result.service.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/results")
@Tag(name = "result", description = "결과화면 API")
public class ResultController {

    private final ResultService resultService;

    // 임시매개변수-> 멤버 아이디
    @GetMapping("/lists")
    @Operation(summary = "전체 결과 화면 조회 API", description = "전체 결과 화면을 조회합니다.")
    public ApiResponse<?> getResultList(){
        return new ApiResponse<>(resultService.getResultList());
    }

    @GetMapping("/{resultId}")
    public ApiResponse<?> getResultDetail(@PathVariable("resultId") Long resultId){
        return new ApiResponse<>(resultService.getResultDetail(resultId));
    }
}