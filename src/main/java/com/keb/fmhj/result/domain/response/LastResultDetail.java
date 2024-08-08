package com.keb.fmhj.result.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Getter
@Setter
public class LastResultDetail {

    private Long memberId;
    private String faceImage;
    private String details;
    private LocalDateTime resultDate;
    private Map<String, Double> probabilities;
}
