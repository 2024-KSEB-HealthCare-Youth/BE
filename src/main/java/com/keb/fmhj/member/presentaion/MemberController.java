package com.keb.fmhj.member.presentaion;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.global.response.ApiResponse;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.dto.request.MypageReqeustDto;
import com.keb.fmhj.member.domain.dto.request.SignUpDto;
import com.keb.fmhj.member.domain.dto.request.UpdateMemberDto;
import com.keb.fmhj.member.domain.dto.response.MemberDetailDto;
import com.keb.fmhj.member.domain.dto.response.MypageResponseDto;
import com.keb.fmhj.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(name = "member", description = "회원 관련 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    @Operation(summary = "회원 등록 API", description = "회원 등록을 합니다.")
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

    @GetMapping("/{memberId}")
    @Operation(summary = "단일 회원 상세 조회 API", description = "한명의 회원을 조회합니다.")
    public ApiResponse<MemberDetailDto> getMemberDetail(@PathVariable(name = "memberId") String loginId) {
        MemberDetailDto memberDetail = memberService.getMemberDetails(loginId);
        return new ApiResponse<>(memberDetail);
    }

    @GetMapping
    @Operation(summary = "전체 회원 상세 조회 API", description = "전체 회원을 조회합니다.")
    public ApiResponse<MemberDetailDto> getAllMemberDetails() {
        List<MemberDetailDto> members = memberService.getAllMemberDetails();
        return new ApiResponse<>(members);
    }

    @PutMapping("/{memberId}")
    @Operation(summary = "회원 수정 API", description = "회원 정보를 수정합니다.")
    public ApiResponse<UpdateMemberDto> updateMember(
            @PathVariable("memberId") String loginId,
            @Validated @RequestBody UpdateMemberDto updateDto) {

        memberService.updateMember(loginId, updateDto);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @DeleteMapping("/{memberId}")
    @Operation(summary = "회원 삭제 API", description = "회원을 삭제합니다.")
    public ApiResponse<Void> deleteMember(@PathVariable("memberId") String loginId) {
        memberService.deleteMember(loginId);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    //마이페이지 조회
    @PostMapping("/mypage")
    public ApiResponse<MypageResponseDto> getMypage(@RequestBody MypageReqeustDto mypageReqeustDto){

        return new ApiResponse<>(memberService.getMypage(mypageReqeustDto));
    }
}
