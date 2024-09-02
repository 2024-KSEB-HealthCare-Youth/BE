package com.keb.fmhj.result.domain.response;

import com.keb.fmhj.result.domain.AdvancedSkinType;
import com.keb.fmhj.result.domain.BasicSkinType;
import com.keb.fmhj.result.domain.Result;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
public class SavedResult {

    private String resultDetails;
    private String faceImage;
    private BasicSkinType basicSkinType;
    private List<AdvancedSkinType> advancedSkinType;
    private Map<String, Double> probabilities;

    public static SavedResult toDto(Result result) {

        HashMap<String, Double> probabilities = new HashMap<>();
        for (Map.Entry<String, Double> probability : result.getProbability().entrySet()) {
            String symptom = probability.getKey();
            Double probabilityValue = probability.getValue();
            probabilities.put(symptom, probabilityValue);
        }

        return SavedResult.builder().resultDetails(result.getDetails())
                .faceImage(result.getFaceImage())
                .basicSkinType(result.getBasicSkinType())
                .advancedSkinType(result.getAdvancedSkinType())
                .probabilities(probabilities).build();

    }
}
