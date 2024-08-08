package com.keb.fmhj.member.domain.dto.request;

import com.keb.fmhj.member.domain.Gender;
import com.keb.fmhj.member.domain.IsAdmin;
import com.keb.fmhj.member.domain.Member;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    @NotNull(message = "아이디를 입력해주세요!")
    private String loginId;

    @NotNull(message = "비밀번호를 입력해주세요!")
    private String password;

    private String name;

    private String nickName;

    private Gender gender;

    private Integer age;

    private String phoneNumber;

    private String email;

    private String profileImage;

    @Builder.Default
    private IsAdmin isAdmin = IsAdmin.USER;
}
