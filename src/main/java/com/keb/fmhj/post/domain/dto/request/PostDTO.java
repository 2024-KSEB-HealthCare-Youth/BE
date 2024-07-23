package com.keb.fmhj.post.domain.dto.request;

import com.keb.fmhj.member.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private String title;
    private String content;
    private String category;
    private Member member;
}