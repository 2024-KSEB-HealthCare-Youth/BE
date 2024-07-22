package com.keb.fmhj.member.presentaion;

import com.keb.fmhj.global.response.ApiResponse;
import com.keb.fmhj.member.domain.dto.response.MemberDetailDto;
import com.keb.fmhj.member.service.MemberSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberSearchController {

    private final MemberSearchService memberSearchService;

    /**
     * 단일 회원 상세 조회
     */
    @GetMapping("/{memberId}")
    public ApiResponse<MemberDetailDto> getMemberDetail(@PathVariable(name = "memberId") String loginId) {
        MemberDetailDto memberDetail = memberSearchService.getMemberDetails(loginId);
        return new ApiResponse<>(memberDetail);
    }

    /**
     * 전체 회원 목록 조회
     */
    @GetMapping
    public ApiResponse<MemberDetailDto> getAllMemberDetails() {
        List<MemberDetailDto> members = memberSearchService.getAllMemberDetails();
        return new ApiResponse<>(members);
    }
}
