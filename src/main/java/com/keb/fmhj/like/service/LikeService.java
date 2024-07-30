package com.keb.fmhj.like.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.like.domain.Like;
import com.keb.fmhj.like.domain.repository.LikeRepository;
import com.keb.fmhj.like.domain.request.LikeRequestDto;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import com.keb.fmhj.post.domain.Post;
import com.keb.fmhj.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    // 좋아요 등록
    @Transactional
    public void addLike(String loginId, Long postId, LikeRequestDto likeDto) {

        Member member = ensureMemberExists(loginId);
        Post post = ensurePostExists(postId);

        Like like = Like.builder()
                .member(member)
                .post(post)
                .build();

        likeRepository.save(like);

        post.setLikeCount(post.getLikeCount() == null ? 1 : post.getLikeCount() + 1);
        postRepository.save(post);
    }

    // 좋아요 삭제
    @Transactional
    public void deleteLike(String loginId, Long postId, LikeRequestDto likeDto) {

        Member member = ensureMemberExists(loginId);
        Post post = ensurePostExists(postId);

        Like like = Like.builder()
                .member(member)
                .post(post)
                .build();

        likeRepository.delete(like);

        // 게시글의 좋아요 수 감소
        if (post.getLikeCount() != null && post.getLikeCount() > 0) {
            post.setLikeCount(post.getLikeCount() - 1);
            postRepository.save(post);
        } else {
            // 예외 처리: 좋아요 수가 이미 0인 경우
            throw YouthException.from(ErrorCode.LIKE_NOT_FOUND);
        }
        postRepository.save(post);
    }

    // 회원 존재 유무 검증
    private Member ensureMemberExists(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> YouthException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    // 게시글 존재 유무 검증
    private Post ensurePostExists(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> YouthException.from(ErrorCode.POST_NOT_FOUND));
    }
}
