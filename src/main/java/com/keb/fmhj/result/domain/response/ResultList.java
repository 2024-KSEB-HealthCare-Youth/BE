package com.keb.fmhj.result.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Builder
@Getter
@Setter
public class ResultList {
    private Long resultId;
    private LocalDateTime resultDate;
}
