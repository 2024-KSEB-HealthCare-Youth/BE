package com.keb.fmhj.resultItem.domain.dto.response;

import com.keb.fmhj.result.domain.AdvancedSkinType;
import com.keb.fmhj.result.domain.BasicSkinType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class RecommendData {

    private String name;
    private BasicSkinType basicSkinType;
    private List<AdvancedSkinType> advancedSkinType;
    private List<String> cosNames;
    private List<String> cosPaths;
    private List<String> nutrNames;
    private List<String> nutrPaths;
}
