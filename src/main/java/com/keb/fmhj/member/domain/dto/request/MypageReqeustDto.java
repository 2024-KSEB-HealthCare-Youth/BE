package com.keb.fmhj.member.domain.dto.request;


import com.keb.fmhj.result.domain.AdvancedSkinType;
import com.keb.fmhj.result.domain.BasicSkinType;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class MypageReqeustDto {

    private String resultImage;
    private String resultDetails;
    private String faceImage;
    private BasicSkinType basicSkinType;
    private List<AdvancedSkinType> advancedSkinType;
    private List<String> cosNames;
    private List<String> cosPaths;
    private List<String> nutrNames;
    private List<String> nutrPaths;

    public MypageReqeustDto(String resultImage, String resultDetails, String faceImage, BasicSkinType basicSkinType, List<AdvancedSkinType> advancedSkinType) {
        this.resultImage = resultImage;
        this.resultDetails = resultDetails;
        this.faceImage = faceImage;
        this.basicSkinType = basicSkinType;
        this.advancedSkinType = advancedSkinType;
    }
}
