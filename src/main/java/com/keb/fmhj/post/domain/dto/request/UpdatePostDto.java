package com.keb.fmhj.post.domain.dto.request;

import com.keb.fmhj.post.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostDto {

    @NotBlank(message = "제목을 입력해주세요!")
    private String title;

    @NotBlank(message = "내용을 입력해주세요!")
    private String content;

    @NotNull(message = "카테고리를 입력해주세요!")
    private Category category;
}
