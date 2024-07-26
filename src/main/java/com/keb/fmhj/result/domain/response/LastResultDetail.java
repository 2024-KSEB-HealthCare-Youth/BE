package com.keb.fmhj.result.domain.response;

import com.keb.fmhj.result.domain.AdvancedSkinType;
import com.keb.fmhj.result.domain.BasicSkinType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class LastResultDetail {
    private Long resultId;
    private Long memberId;
    private String resultImage;
    private String faceImage;
    private String details;
    private LocalDateTime resultDate;
}
