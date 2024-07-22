package com.keb.fmhj.member.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.dto.request.SignInDto;
import com.keb.fmhj.member.domain.dto.request.SignUpDto;
import com.keb.fmhj.member.domain.dto.response.MemberDetailDto;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원 등록
     */
    public void join(SignUpDto signUpDtoDto) {

        validateExistMember(signUpDtoDto);
        Member joinMember = Member.builder()
                .loginId(signUpDtoDto.getLoginId())
                .password(bCryptPasswordEncoder.encode(signUpDtoDto.getPassword()))
                .name(signUpDtoDto.getName())
                .nickname(signUpDtoDto.getNickName())
                .gender(signUpDtoDto.getGender())
                .age(signUpDtoDto.getAge())
                .phoneNumber(signUpDtoDto.getPhoneNumber())
                .email(signUpDtoDto.getEmail())
                .profileImage(signUpDtoDto.getProfileImage())
                .build();
        memberRepository.save(joinMember);
    }

    private void validateExistMember(SignUpDto joinDto) {
        String loginId = joinDto.getLoginId();
        if (memberRepository.existsByLoginId(loginId)) {
            throw YouthException.from(ErrorCode.USER_NOT_FOUND);
        }
    }

    /**
     * 회원 수정
     */
//    @Transactional
//    public void updateMember(String loginId, UpdateMemberDto requestDto) {
//
//        if(memberRepository.existsByLoginId(loginId)) {
//            throw YouthException.from(ErrorCode.USER_NOT_FOUND);
//        }
//
//        Optional<Member> member = memberRepository.findByLoginId(loginId);
//        member.get().update(requestDto.getName(), requestDto.getNickName(), requestDto.getPhoneNumber(),
//                requestDto.getEmail(), requestDto.getProfileImage());
//
//        memberRepository.save(requestDto);
//    }

    /**
     * 회원 조회
     */
    public MemberDetailDto findByLoginId(String memberId) {
        Member entity = memberRepository.findByLoginId(memberId).orElseThrow(()
                -> new IllegalArgumentException("해당 회원은 존재하지 않습니다."));
        return MemberDetailDto.toDto(entity);
    }

    /**
     * 전체 회원 조회
     */
    public List<MemberDetailDto> findAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberDetailDto::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public String deleteMember(String loginId) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() ->
                new IllegalArgumentException("해당 회원은 존재하지 않습니다: " + loginId));
        memberRepository.delete(member);
        return loginId;
    }

    public MemberDetailDto login(SignInDto signInDto) {
        Member member = memberRepository.findByLoginId(signInDto.getLoginId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with login ID: " + signInDto.getLoginId()));

        if (!bCryptPasswordEncoder.matches(signInDto.getPassword(), member.getPassword())) {
            throw new IllegalStateException("Invalid credentials");
        }

        return MemberDetailDto.toDto(member);
    }

    /**
     * 아이디 중복 검증
     */
    private void validateLoginIdDuplicated(String loginId) {
        if(memberRepository.existsByLoginId(loginId)) {
            throw new IllegalStateException("이미 사용중인 로그인 ID입니다.");
        }
    }
}