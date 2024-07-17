package com.keb.fmhj.member.presentaion;

import com.keb.fmhj.member.domain.dto.request.SignInDto;
import com.keb.fmhj.member.domain.dto.request.SignUpDto;
import com.keb.fmhj.member.domain.dto.request.UpdateMemberDto;
import com.keb.fmhj.member.domain.dto.response.MemberDetailDto;
import com.keb.fmhj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MemberDetailDto> addMember(@RequestBody SignUpDto requestDto) {
        Long memberId = memberService.save(requestDto);
        MemberDetailDto memberDetail = memberService.findById(memberId);
        return ResponseEntity.ok(memberDetail);
    }

    /**
     * 회원 조회
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDetailDto> findDetailMember(@PathVariable Long memberId) {
        MemberDetailDto memberDetail = memberService.findById(memberId);
        return ResponseEntity.ok(memberDetail);
    }

    /**
     * 회원 수정
     */
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberDetailDto> updateMember(@PathVariable Long memberId, @RequestBody UpdateMemberDto requestDto) {
        memberService.updateMember(memberId, requestDto);
        MemberDetailDto updatedMember = memberService.findById(memberId);
        return ResponseEntity.ok(updatedMember);
    }

    /**
     * 회원 삭제
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
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