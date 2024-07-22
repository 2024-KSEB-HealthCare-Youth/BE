package com.keb.fmhj.member.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class MemberDeleteService {

    private final MemberRepository memberRepository;

    @Transactional
    public void deleteMember(String loginId) {
        if (!memberRepository.existsByLoginId(loginId)) {
            throw YouthException.from(ErrorCode.USER_NOT_FOUND);
        }
        memberRepository.deleteByLoginId(loginId);
    }
}
