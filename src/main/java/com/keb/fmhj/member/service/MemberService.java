package com.keb.fmhj.member.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.dto.request.MypageReqeustDto;
import com.keb.fmhj.member.domain.dto.request.SignUpDto;
import com.keb.fmhj.member.domain.dto.request.UpdateMemberDto;
import com.keb.fmhj.member.domain.dto.response.MemberDetailDto;
import com.keb.fmhj.member.domain.dto.response.MypageResponseDto;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import com.keb.fmhj.result.domain.Result;
import com.keb.fmhj.result.domain.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ResultRepository resultRepository;

    // 회원 등록
    @Transactional
    public void join(SignUpDto signUpDtoDto) {

        validateExistMember(signUpDtoDto);

        Member joinMember = Member.builder()
                .loginId(signUpDtoDto.getLoginId())
                .password(bCryptPasswordEncoder.encode(signUpDtoDto.getPassword()))
                .name(signUpDtoDto.getName())
                .nickName(signUpDtoDto.getNickName())
                .gender(signUpDtoDto.getGender())
                .age(signUpDtoDto.getAge())
                .phoneNumber(signUpDtoDto.getPhoneNumber())
                .email(signUpDtoDto.getEmail())
                .profileImage(signUpDtoDto.getProfileImage())
                .isAdmin(signUpDtoDto.getIsAdmin())
                .build();

        memberRepository.save(joinMember);
    }

    // 단일 회원 조회
    @Transactional(readOnly = true)
    public MemberDetailDto getMemberDetails(String loginId) {

        Member member = ensureMemberExists(loginId);
        return MemberDetailDto.toDto(member);
    }

    // 전체 회원 조회
    @Transactional(readOnly = true)
    public List<MemberDetailDto> getAllMemberDetails() {

        return memberRepository
                .findAll()
                .stream()
                .map(MemberDetailDto::toDto)
                .collect(Collectors.toList());
    }

    // 회원 수정
    @Transactional
    public void updateMember(String loginId, UpdateMemberDto updateDto) {

        Member member = ensureMemberExists(loginId);
        validateMemberOwner(member, loginId);

        member.setName(updateDto.getName());
        member.setNickName(updateDto.getNickName());
        member.setEmail(updateDto.getEmail());
        member.setPhoneNumber(updateDto.getPhoneNumber());
        member.setProfileImage(updateDto.getProfileImage());

        memberRepository.save(member);
    }

    // 회원 삭제
    @Transactional
    public void deleteMember(String loginId) {

        Member member = ensureMemberExists(loginId);
        validateMemberOwner(member, loginId);

        memberRepository.delete(member);
    }

    // 마이페이지
    @Transactional
    public MypageResponseDto getMypage(MypageReqeustDto reqeustDto){

        Member member = memberRepository.findById(1L).orElseThrow(() -> YouthException.from(ErrorCode.INVALID_REQUEST));

        Result result = Result.builder()
                .member(member)
                .advancedSkinType(reqeustDto.getAdvancedSkinType())
                .basicSkinType(reqeustDto.getBasicSkinType())
                .resultImage(reqeustDto.getResultImage())
                .faceImage(reqeustDto.getFaceImage())
                .build();

        resultRepository.save(result);

        return MypageResponseDto.builder()
                .name(member.getName())
                .nickname(member.getName())
                .gender(member.getGender())
                .age(member.getAge())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .resultImage(result.getResultImage())
                .resultDetails(result.getDetails())
                .basicSkinType(result.getBasicSkinType())
                .advancedSkinType(result.getAdvancedSkinType())
                .build();
    }

    // 회원 존재 유무 검증
    private Member ensureMemberExists(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> YouthException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    // 회원 본인인지 검증
    private void validateMemberOwner(Member member, String loginId) {
        if(member.getLoginId().equals(loginId)){
            throw YouthException.from(ErrorCode.MEMBER_NOT_AUTHENTICATED);
        }
    }

    // 회원 존재 유무 검증
    private void validateExistMember(SignUpDto joinDto) {

        String loginId = joinDto.getLoginId();
        if (memberRepository.existsByLoginId(loginId)) {
            throw YouthException.from(ErrorCode.DUPLICATE_MEMBER_LOGIN_ID);
        }
    }
}
