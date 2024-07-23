package com.keb.fmhj.result.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResultDetail {
    private Long resultId;
    private Long memberId;
    private String resultImage;
    private String faceImage;
    private String details;
}
