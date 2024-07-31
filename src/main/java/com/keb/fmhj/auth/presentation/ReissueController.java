package com.keb.fmhj.auth.presentation;

import com.keb.fmhj.auth.service.ReissueService;
import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reissue")
public class ReissueController {

    private final ReissueService reissueService;

    @PostMapping
    @Operation(
            summary = "토큰 재발급 API",
            description = "만료된 access token 대신 refresh token을 재발급합니다."
    )
    ApiResponse reissue(HttpServletRequest request, HttpServletResponse response){

        String newAccess = reissueService.createNewAccessToken(request,response);
        response.addHeader("Authorization", "Bearer " + newAccess);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }
}
