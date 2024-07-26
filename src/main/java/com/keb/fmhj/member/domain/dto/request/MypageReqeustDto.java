package com.keb.fmhj.member.domain.dto.request;


import com.keb.fmhj.result.domain.AdvancedSkinType;
import com.keb.fmhj.result.domain.BasicSkinType;
import lombok.*;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class MypageReqeustDto {

    private String resultImage;
    private String resultDetails;
    private String faceImage;
    private BasicSkinType basicSkinType;
    private AdvancedSkinType advancedSkinType;

    @Builder
    public MypageReqeustDto(String resultImage, String resultDetails, String faceImage, BasicSkinType basicSkinType, AdvancedSkinType advancedSkinType) {
        this.resultImage = resultImage;
        this.resultDetails = resultDetails;
        this.faceImage = faceImage;
        this.basicSkinType = basicSkinType;
        this.advancedSkinType = advancedSkinType;
    }
}
