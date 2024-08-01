package com.keb.fmhj.comment.domain.dto.response;

import com.keb.fmhj.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDetailDto {

    private Long postId;
    private Long commentId;
    private String nickName;
    private String profileImage;
    private String content;
    private LocalDateTime createdAt;

    public static CommentDetailDto toDto(Comment comment) {
        return CommentDetailDto.builder()
                .postId(comment.getPost().getPostId())
                .commentId(comment.getCommentId())
                .nickName(comment.getMember().getNickName())
                .profileImage(comment.getMember().getProfileImage())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
