package com.keb.fmhj.like.domain.request;

import com.keb.fmhj.like.domain.Like;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.post.domain.Post;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeRequestDto {

    private Member member;

    private Post post;

    public static Like toEntity(LikeRequestDto request) {
        return Like.builder()
                .member(request.getMember())
                .post(request.getPost())
                .build();
    }
}
