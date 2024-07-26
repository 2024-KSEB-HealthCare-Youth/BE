package com.keb.fmhj.result.presentaion;


import com.keb.fmhj.global.response.ApiResponse;
import com.keb.fmhj.result.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/results")
public class ResultController {

    private final ResultService resultService;

    //임시매개변수-> 멤버 아이디
    @GetMapping("/lists")
    public ApiResponse<?> getResultList(){
        return new ApiResponse<>(resultService.getResultList());
    }

    @GetMapping("/{resultId}")
    public ApiResponse<?> getResultDetail(@PathVariable Long resultId){
        return new ApiResponse<>(resultService.getResultDetail(resultId));
    }
}
