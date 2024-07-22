package com.keb.fmhj.member.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.dto.request.SignUpDto;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class MemberJoinService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원 등록
    public void join(SignUpDto signUpDtoDto) {

        validateExistMember(signUpDtoDto);
        Member joinMember = Member.builder()
                .loginId(signUpDtoDto.getLoginId())
                .password(bCryptPasswordEncoder.encode(signUpDtoDto.getPassword()))
                .name(signUpDtoDto.getName())
                .gender(signUpDtoDto.getGender())
                .age(signUpDtoDto.getAge())
                .phoneNumber(signUpDtoDto.getPhoneNumber())
                .email(signUpDtoDto.getEmail())
                .profileImage(signUpDtoDto.getProfileImage())
                .build();
        memberRepository.save(joinMember);
    }

    // 회원 존재 유무
    private void validateExistMember(SignUpDto joinDto) {
        String loginId = joinDto.getLoginId();
        if (memberRepository.existsByLoginId(loginId)) {
            throw YouthException.from(ErrorCode.USER_NOT_FOUND);
        }
    }
}