package com.keb.fmhj.post.domain.dto.request;

import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.post.domain.Category;
import com.keb.fmhj.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    private Category category;

    private Member member;

    public static Post toEntity(PostDTO postDTO) {
        return Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .category(postDTO.getCategory())
                .member(postDTO.getMember())
                .build();
    }

}