package com.keb.fmhj.member.domain.dto.request;


import com.keb.fmhj.result.domain.AdvancedSkinType;
import com.keb.fmhj.result.domain.BasicSkinType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class MypageReqeustDto {

    private String resultDetails;
    private String faceImage;
    private BasicSkinType basicSkinType;
    private List<AdvancedSkinType> advancedSkinType;
    private List<String> cosNames;
    private List<String> cosPaths;
    private List<String> nutrNames;
    private List<String> nutrPaths;
    private Map<String,Double> probabilities;

    public MypageReqeustDto(String resultDetails, String faceImage, BasicSkinType basicSkinType, List<AdvancedSkinType> advancedSkinType) {

        this.resultDetails = resultDetails;
        this.faceImage = faceImage;
        this.basicSkinType = basicSkinType;
        this.advancedSkinType = advancedSkinType;
    }
}
