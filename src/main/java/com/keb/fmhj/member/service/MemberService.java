package com.keb.fmhj.member.service;

import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.dto.request.SignInDto;
import com.keb.fmhj.member.domain.dto.request.SignUpDto;
import com.keb.fmhj.member.domain.dto.request.UpdateMemberDto;
import com.keb.fmhj.member.domain.dto.response.MemberDetailDto;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원 등록
     */
    @Transactional
    public Long save(SignUpDto requestDto) {
        validateLoginIdDuplicated(requestDto.getLoginId());
        return memberRepository.save(SignUpDto.toEntity(requestDto)).getId();
    }

    /**
     * 회원 수정
     */
    @Transactional
    public Long updateMember(Long id, UpdateMemberDto requestDto) {
        Member member = memberRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 회원은 존재하지 않습니다."));
        member.update(requestDto);
        return memberRepository.save(member).getId();
    }

    /**
     * 회원 조회
     */
    public MemberDetailDto findById(Long id) {
        Member entity = memberRepository.findById(id).orElseThrow(()
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
    public Long deleteMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 회원은 존재하지 않습니다: " + id));
        memberRepository.delete(member);
        return id;
    }

    public MemberDetailDto login(SignInDto signInDto) {
        Member member = memberRepository.findByLoginId(signInDto.getLoginId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with login ID: " + signInDto.getLoginId()));

        if (!passwordEncoder.matches(signInDto.getPassword(), member.getPassword())) {
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