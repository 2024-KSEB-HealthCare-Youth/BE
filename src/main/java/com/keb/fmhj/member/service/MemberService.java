package com.keb.fmhj.member.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.global.response.ApiResponse;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.dto.request.MypageReqeustDto;
import com.keb.fmhj.member.domain.dto.request.SignUpDto;
import com.keb.fmhj.member.domain.dto.request.UpdateMemberDto;
import com.keb.fmhj.member.domain.dto.response.MemberDetailDto;
import com.keb.fmhj.member.domain.dto.response.MypageResponseDto;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import com.keb.fmhj.result.domain.Result;
import com.keb.fmhj.result.domain.repository.ResultRepository;
import com.keb.fmhj.result.domain.response.LastResultDetail;
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
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ResultRepository resultRepository;

    // 회원 등록
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

    // 회원 존재 유무
    private void validateExistMember(SignUpDto joinDto) {
        String loginId = joinDto.getLoginId();
        if (memberRepository.existsByLoginId(loginId)) {
            throw YouthException.from(ErrorCode.USER_NOT_FOUND);
        }
    }

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

    @Transactional
    public void updateMember(String loginId, UpdateMemberDto updateDto) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> YouthException.from(ErrorCode.USER_NOT_FOUND));

        member.setName(updateDto.getName());
        member.setNickName(updateDto.getNickName());
        member.setEmail(updateDto.getEmail());
        member.setPhoneNumber(updateDto.getPhoneNumber());
        member.setProfileImage(updateDto.getProfileImage());

        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(String loginId) {
        if (!memberRepository.existsByLoginId(loginId)) {
            throw YouthException.from(ErrorCode.USER_NOT_FOUND);
        }
        memberRepository.deleteByLoginId(loginId);
    }

    public MypageResponseDto getMypage(MypageReqeustDto mypageReqeustDto){
        Member member = memberRepository.findById(1l).orElseThrow(() -> YouthException.from(ErrorCode.INVALID_REQUEST));
        Result result = Result.builder()
                .member(member)
                .advancedSkinType(mypageReqeustDto.getAdvancedSkinType())
                .basicSkinType(mypageReqeustDto.getBasicSkinType())
                .resultImage(mypageReqeustDto.getResultImage())
                .faceImage(mypageReqeustDto.getFaceImage())
                .build();

        resultRepository.save(result);

        MypageResponseDto mypageResponseDto = MypageResponseDto.builder()
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

        return mypageResponseDto;
    }

}