package com.keb.fmhj.member.presentation;

import com.keb.fmhj.auth.utils.AccessTokenUtils;
import com.keb.fmhj.global.exception.ErrorCode;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(name = "member", description = "회원 관련 API")
public class MemberController {

    private final AccessTokenUtils accessTokenUtils;
    private final MemberService memberService;

    @PostMapping("/join")
    @Operation(summary = "회원 등록 API", description = "회원 등록을 합니다.")
    public ApiResponse<Member> join(@Validated @RequestBody SignUpDto memberJoinRequest) {

        memberService.join(memberJoinRequest);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @GetMapping("/me")
    @Operation(summary = "단일 회원 상세 조회 API", description = "한명의 회원을 조회합니다.")
    public ApiResponse<MemberDetailDto> getMemberDetail() {

        String loginId = AccessTokenUtils.isPermission();
        MemberDetailDto memberDetail = memberService.getMemberDetails(loginId);
        return new ApiResponse<>(memberDetail);
    }

    @GetMapping
    @Operation(summary = "전체 회원 상세 조회 API", description = "전체 회원을 조회합니다.")
    public ApiResponse<MemberDetailDto> getAllMemberDetails() {

        List<MemberDetailDto> members = memberService.getAllMemberDetails();
        return new ApiResponse<>(members);
    }

    @PutMapping("/me")
    @Operation(summary = "회원 수정 API", description = "회원 정보를 수정합니다.")
    public ApiResponse<UpdateMemberDto> updateMember(
            @Validated @RequestBody UpdateMemberDto updateDto) {

        String loginId = AccessTokenUtils.isPermission();
        memberService.updateMember(loginId, updateDto);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @DeleteMapping("/me")
    @Operation(summary = "회원 삭제 API", description = "회원을 삭제합니다.")
    public ApiResponse<Void> deleteMember() {

        String loginId = AccessTokenUtils.isPermission();
        memberService.deleteMember(loginId);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @PostMapping("/mypage")
    @Operation(summary = "진단 결과 저장 API", description = "AI 진단 결과를 저장합니다.")
    public ApiResponse<MypageResponseDto> saveResult(@RequestBody MypageReqeustDto mypageReqeustDto){

        return new ApiResponse<>(memberService.saveResult(accessTokenUtils.isPermission(), mypageReqeustDto));
    }

    @GetMapping("/mypage")
    @Operation(summary = "마이페이지 API", description = "진단 결과를 포함한 마이페이지를 불러옵니다.")
    public ApiResponse<MypageResponseDto> getMyPage(){

        return new ApiResponse<>(memberService.getMyPage(accessTokenUtils.isPermission()));
    }
}
