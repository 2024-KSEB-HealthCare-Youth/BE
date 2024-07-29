package com.keb.fmhj.comment.domain.dto.request;

import com.keb.fmhj.comment.domain.Comment;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.post.domain.Post;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentDto {

    @NotNull(message = "내용을 입력해주세요!")
    private String content;

    private Post post;

    private Member member;

    public static Comment toEntity(AddCommentDto request){
        return Comment.builder()
                .content(request.getContent())
                .post(request.getPost())
                .member(request.getMember())
                .build();
    }
}