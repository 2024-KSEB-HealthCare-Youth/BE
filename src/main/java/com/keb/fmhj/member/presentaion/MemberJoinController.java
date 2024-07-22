package com.keb.fmhj.member.presentaion;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.global.response.ApiResponse;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.dto.request.SignInDto;
import com.keb.fmhj.member.domain.dto.request.SignUpDto;
import com.keb.fmhj.member.domain.dto.response.MemberDetailDto;
import com.keb.fmhj.member.service.MemberJoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberJoinController {

    private final MemberJoinService memberJoinService;

    /**
     * 회원 등록
     */
    @PostMapping
    public ApiResponse<Member> join(@Validated @RequestBody SignUpDto memberJoinRequest, Errors errors) {
        validateRequest(errors);
        memberJoinService.join(memberJoinRequest);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    private void validateRequest(Errors errors) {
        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(error -> {
                String errorMessage = error.getDefaultMessage();
                throw YouthException.from(ErrorCode.INVALID_REQUEST);
            });
        }
    }
}