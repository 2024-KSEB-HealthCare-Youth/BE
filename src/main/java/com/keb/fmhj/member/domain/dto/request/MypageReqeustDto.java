package com.keb.fmhj.member.domain.dto.request;


import com.keb.fmhj.result.domain.AdvancedSkinType;
import com.keb.fmhj.result.domain.BasicSkinType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MypageReqeustDto {

    private String resultImage;
    private String resultDetails;
    private String faceImage;
    private BasicSkinType basicSkinType;
    private AdvancedSkinType advancedSkinType;
}
