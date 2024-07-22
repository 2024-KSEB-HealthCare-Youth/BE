package com.keb.fmhj.member.presentaion;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.global.response.ApiResponse;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.dto.request.SignInDto;
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

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 등록
     */
    @PostMapping
    public ApiResponse<Member> join(@Validated @RequestBody SignUpDto memberJoinRequest, Errors errors) {
        validateRequest(errors);
        memberService.join(memberJoinRequest);
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

    /**
     * 회원 조회
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDetailDto> findDetailMember(@PathVariable String memberId) {
        MemberDetailDto memberDetail = memberService.findByLoginId(memberId);
        return ResponseEntity.ok(memberDetail);
    }

    /**
     * 회원 수정
     */
//    @PutMapping("/{memberId}")
//    public ResponseEntity<MemberDetailDto> updateMember(@PathVariable String memberId, @RequestBody UpdateMemberDto requestDto) {
//        memberService.updateMember(memberId, requestDto);
//        MemberDetailDto updatedMember = memberService.findByLoginId(memberId);
//        return ResponseEntity.ok(updatedMember);
//    }

    /**
     * 회원 삭제
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable String memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok().build();
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<MemberDetailDto> login(@RequestBody SignInDto signInDto) {
        MemberDetailDto memberDetail = memberService.login(signInDto);
        return ResponseEntity.ok(memberDetail);
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // 여기 추가해야됨
        return ResponseEntity.ok().build();
    }
}