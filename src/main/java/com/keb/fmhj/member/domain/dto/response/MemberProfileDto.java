package com.keb.fmhj.member.domain.dto.response;

import com.keb.fmhj.member.domain.Gender;
import com.keb.fmhj.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberProfileDto {

    private String name;
    private String nickName;
    private Gender gender;
    private Integer age;
    private String profileImage;

    public static MemberProfileDto toDto(Member member) {
        return MemberProfileDto.builder()
                .name(member.getName())
                .nickName(member.getName())
                .gender(member.getGender())
                .age(member.getAge())
                .profileImage(member.getProfileImage())
                .build();
    }
}