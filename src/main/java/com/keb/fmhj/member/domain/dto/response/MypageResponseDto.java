package com.keb.fmhj.member.domain.dto.response;

import com.keb.fmhj.member.domain.Gender;
import com.keb.fmhj.result.domain.AdvancedSkinType;
import com.keb.fmhj.result.domain.BasicSkinType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MypageResponseDto {

    private String name;
    private String nickname;
    private Gender gender;
    private int age;
    private String email;
    private String phoneNumber;
    private String resultImage;
    private String resultDetails;
    private AdvancedSkinType advancedSkinType;
    private BasicSkinType basicSkinType;
}
