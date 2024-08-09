package com.keb.fmhj.member.domain.dto.request;


import com.keb.fmhj.member.domain.Gender;
import com.keb.fmhj.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberDto {

    @Schema(description = "이름", example = "김개똥")
    private String name;

    @Schema(description = "닉네임", example = "1234")
    private String nickName;

    @Schema(description = "이메일", example = "youth@gamil.com")
    private String email;

    @Schema(description = "전화번호", example = "01012345678")
    private String phoneNumber;

    @Schema(description = "나이", example = "21")
    private Integer age;

    @Schema(description = "성별", example = "MALE")
    private Gender gender;

    @Schema(description = "프로필 이미지", example = "https://Desktop/face.jpeg")
    private String profileImage;
}