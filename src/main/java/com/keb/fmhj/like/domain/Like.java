package com.keb.fmhj.like.domain;

import com.keb.fmhj.global.BaseTimeEntity;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "likes")
public class Like extends BaseTimeEntity {

    @Id
    @Column(name = "like_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
