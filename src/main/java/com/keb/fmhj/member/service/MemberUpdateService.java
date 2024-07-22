package com.keb.fmhj.member.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.dto.request.UpdateMemberDto;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberUpdateService {

    private final MemberRepository memberRepository;

    @Transactional
    public void updateMember(String loginId, UpdateMemberDto updateDto) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> YouthException.from(ErrorCode.USER_NOT_FOUND));

        member.setName(updateDto.getName());
        member.setEmail(updateDto.getEmail());
        member.setPhoneNumber(updateDto.getPhoneNumber());
        member.setProfileImage(updateDto.getProfileImage());

        memberRepository.save(member);
    }
}
