package com.keb.fmhj.comment.domain.dto.response;

import com.keb.fmhj.comment.domain.Comment;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDetailDto {

    private String content;
    private Post post;
    private Member member;

    public static CommentDetailDto toDto(Comment comment) {
        return CommentDetailDto.builder()
                .content(comment.getContent())
                .post(comment.getPost())
                .member(comment.getMember())
                .build();
    }
}
