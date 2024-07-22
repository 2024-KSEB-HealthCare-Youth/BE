package com.keb.fmhj.member.presentaion;

import com.keb.fmhj.member.domain.dto.request.UpdateMemberDto;
import com.keb.fmhj.member.service.MemberUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberUpdateController {

    private final MemberUpdateService memberUpdateService;

    @PutMapping("/{memberId}")
    public ResponseEntity<Void> updateMember(
            @PathVariable("memberId") String loginId,
            @Validated @RequestBody UpdateMemberDto updateDto) {

        memberUpdateService.updateMember(loginId, updateDto);
        return ResponseEntity.ok().build();
    }
}
