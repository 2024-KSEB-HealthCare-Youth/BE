package com.keb.fmhj.member.domain.dto.response;

import com.keb.fmhj.member.domain.Gender;
import com.keb.fmhj.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDetailDto {

    private String loginId;
    private String name;
    private String nickName;
    private Gender gender;
    private Integer age;
    private String phoneNumber;
    private String email;
    private String profileImage;

    public static MemberDetailDto toDto(Member member) {
        return MemberDetailDto.builder()
                .loginId(member.getLoginId())
                .name(member.getName())
                .nickName(member.getNickName())
                .gender(member.getGender())
                .age(member.getAge())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .profileImage(member.getProfileImage())
                .build();
    }
}
