package com.keb.fmhj.member.domain.dto.request;

import com.keb.fmhj.member.domain.Gender;
import com.keb.fmhj.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    @NotNull(message = "아이디를 입력해주세요!")
    private String loginId;

    @NotNull(message = "비밀번호를 입력해주세요!")
    private String password;

    @NotNull(message = "이름를 입력해주세요!")
    private String name;

    @NotNull(message = "성별을 선택해주세요!")
    private Gender gender;

    @NotNull(message = "나이를 입력해주세요!")
    private Integer age;

    @NotNull(message = "전화번호를 입력해주세요!")
    private String phoneNumber;

    private String email;

    private String profileImage;

    public static Member toEntity(SignUpDto requestDto) {
        return Member.builder()
                .loginId(requestDto.getLoginId())
                .password(requestDto.getPassword())
                .name(requestDto.getName())
                .gender(requestDto.getGender())
                .age(requestDto.getAge())
                .phoneNumber(requestDto.getPhoneNumber())
                .email(requestDto.getEmail())
                .profileImage(requestDto.getProfileImage())
                .build();
    }
}
