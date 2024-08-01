package com.keb.fmhj.post.domain.dto.response;

import com.keb.fmhj.comment.domain.dto.response.CommentDetailDto;
import com.keb.fmhj.post.domain.Category;
import com.keb.fmhj.post.domain.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OnePostDetailDto {

    private Long postId;
    private String title;
    private Long likeCount;
    private Category category;
    private String profileImage;
    private String nickName;
    private List<CommentDetailDto> comments;
    private LocalDateTime createdAt;

    public static OnePostDetailDto toDto(Post post) {
        return OnePostDetailDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .likeCount(post.getLikeCount())
                .category(post.getCategory())
                .profileImage(post.getMember().getProfileImage())
                .nickName(post.getMember().getNickName())
                .comments(post.getComments()
                        .stream()
                        .map(CommentDetailDto::toDto)
                        .collect(Collectors.toList()))
                .createdAt(post.getCreatedAt())
                .build();
    }
}
