package com.keb.fmhj.comment.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentDto {

    @Schema(description = "내용", example = "이 제품 써보니까 좋더라구요!")
    private String content;
}
