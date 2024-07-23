package com.keb.fmhj.member.presentaion;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.global.response.ApiResponse;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.dto.request.SignUpDto;
import com.keb.fmhj.member.domain.dto.request.UpdateMemberDto;
import com.keb.fmhj.member.domain.dto.response.MemberDetailDto;
import com.keb.fmhj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 등록
    @PostMapping
    public ApiResponse<Member> join(@Validated @RequestBody SignUpDto memberJoinRequest, Errors errors) {
        validateRequest(errors);
        memberService.join(memberJoinRequest);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    // 유효성 검증
    private void validateRequest(Errors errors) {
        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(error -> {
                String errorMessage = error.getDefaultMessage();
                throw YouthException.from(ErrorCode.INVALID_REQUEST);
            });
        }
    }

    // 단일 회원 상세 조회
    @GetMapping("/{memberId}")
    public ApiResponse<MemberDetailDto> getMemberDetail(@PathVariable(name = "memberId") String loginId) {
        MemberDetailDto memberDetail = memberService.getMemberDetails(loginId);
        return new ApiResponse<>(memberDetail);
    }

    // 전체 회원 목록 조회
    @GetMapping
    public ApiResponse<MemberDetailDto> getAllMemberDetails() {
        List<MemberDetailDto> members = memberService.getAllMemberDetails();
        return new ApiResponse<>(members);
    }

    // 회원 수정
    @PutMapping("/{memberId}")
    public ApiResponse<UpdateMemberDto> updateMember(
            @PathVariable("memberId") String loginId,
            @Validated @RequestBody UpdateMemberDto updateDto) {

        memberService.updateMember(loginId, updateDto);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    // 회원 삭제
    @DeleteMapping("/{memberId}")
    public ApiResponse<Void> deleteMember(@PathVariable("memberId") String loginId) {
        memberService.deleteMember(loginId);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }
}