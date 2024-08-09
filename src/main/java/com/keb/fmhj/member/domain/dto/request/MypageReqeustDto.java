package com.keb.fmhj.member.domain.dto.request;

import com.keb.fmhj.result.domain.AdvancedSkinType;
import com.keb.fmhj.result.domain.BasicSkinType;
import com.keb.fmhj.result.domain.Result;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MypageReqeustDto {

    private String resultDetails;
    private String faceImage;
    private BasicSkinType basicSkinType;
    private List<AdvancedSkinType> advancedSkinType;
    private List<String> cosNames;
    private List<String> cosPaths;
    private List<String> nutrNames;
    private List<String> nutrPaths;
    private Map<String, Double> probabilities;

    public static Result toEntity(MypageReqeustDto request) {
        return Result.builder()
                .details(request.resultDetails)
                .faceImage(request.faceImage)
                .basicSkinType(request.basicSkinType)
                .advancedSkinType(Optional.ofNullable(request.advancedSkinType).orElse(Collections.emptyList()))
                .build();
    }
}
