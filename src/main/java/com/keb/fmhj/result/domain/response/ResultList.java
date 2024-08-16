package com.keb.fmhj.result.domain.response;

import com.keb.fmhj.result.domain.AdvancedSkinType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class ResultList {
    private Long resultId;
    private LocalDateTime resultDate;
    private List<AdvancedSkinType> advancedSkinTypeList;
}
