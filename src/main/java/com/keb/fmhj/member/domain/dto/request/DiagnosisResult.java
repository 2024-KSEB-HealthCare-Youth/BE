package com.keb.fmhj.member.domain.dto.request;

import com.keb.fmhj.result.domain.AdvancedSkinType;
import com.keb.fmhj.result.domain.BasicSkinType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisResult {

    private String resultDetails;
    private String faceImage;
    private BasicSkinType basicSkinType;
    private List<AdvancedSkinType> advancedSkinType;
    private List<String> cosNames;
    private List<String> cosPaths;
    private List<String> nutrNames;
    private List<String> nutrPaths;
    private Map<String, Double> probabilities;

}
