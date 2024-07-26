package com.keb.fmhj.member.domain.dto;

import com.keb.fmhj.member.domain.Gender;
import com.keb.fmhj.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String loginId;
    private String name;
    private String nickName;
    private Gender gender;
    private int age;
    private String phoneNumber;
    private String email;
    private String profileImage;

    public static MemberDTO fromEntity(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getLoginId(),
                member.getName(),
                member.getNickName(),
                member.getGender(),
                member.getAge(),
                member.getPhoneNumber(),
                member.getEmail(),
                member.getProfileImage()
        );
    }
}