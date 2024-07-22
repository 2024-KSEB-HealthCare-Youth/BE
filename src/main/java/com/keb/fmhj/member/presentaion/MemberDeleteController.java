package com.keb.fmhj.member.presentaion;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.response.ApiResponse;
import com.keb.fmhj.member.service.MemberDeleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberDeleteController {

    private final MemberDeleteService memberDeleteService;

    @DeleteMapping("/{memberId}")
    public ApiResponse<Void> deleteMember(@PathVariable("memberId") String loginId) {
        memberDeleteService.deleteMember(loginId);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }
}
