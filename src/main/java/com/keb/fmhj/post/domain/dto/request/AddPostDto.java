package com.keb.fmhj.post.domain.dto.request;

import com.keb.fmhj.post.domain.Category;
import com.keb.fmhj.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddPostDto {

    @NotBlank(message = "제목을 입력해주세요!")
    private String title;

    @NotBlank(message = "내용을 입력홰주세요!")
    private String content;

    private Category category;

    public static Post toEntity(AddPostDto request) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .build();
    }
}
