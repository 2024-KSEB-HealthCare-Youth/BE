package com.keb.fmhj.post.domain.dto.response;

import com.keb.fmhj.post.domain.Category;
import com.keb.fmhj.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDetailDto {

    private Long postId;
    private String title;
    private String content;

    private Long likeCount;
    private Long commentCount;
    private Long viewCount;

    private Category category;

    private String profileImage;
    private String nickName;

    public static PostDetailDto toDto(Post post) {
        return PostDetailDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .viewCount(post.getViewCount())
                .category(post.getCategory())
                .profileImage(post.getMember().getProfileImage())
                .nickName(post.getMember().getNickName())
                .build();
    }
}
