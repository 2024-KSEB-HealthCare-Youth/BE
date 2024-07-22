package com.keb.fmhj.member.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.dto.response.MemberDetailDto;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class MemberSearchService {

    private final MemberRepository memberRepository;

    // 단일 조회
    @Transactional(readOnly = true)
    public MemberDetailDto getMemberDetails(String loginId) {

        Optional<Member> targetMember = memberRepository.findByLoginId(loginId);

        if (targetMember.isEmpty()) {
            throw YouthException.from(ErrorCode.USER_NOT_FOUND);
        }

        Member findMember = targetMember.get();
        return MemberDetailDto.toDto(findMember);
    }

    // 전체 조회
    public List<MemberDetailDto> getAllMemberDetails() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberDetailDto::toDto)
                .collect(Collectors.toList());
    }

}
