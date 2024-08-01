package com.keb.fmhj.post.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import com.keb.fmhj.post.domain.Post;
import com.keb.fmhj.post.domain.dto.request.AddPostDto;
import com.keb.fmhj.post.domain.dto.request.UpdatePostDto;
import com.keb.fmhj.post.domain.dto.response.OnePostDetailDto;
import com.keb.fmhj.post.domain.dto.response.PostDetailDto;
import com.keb.fmhj.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 게시글 등록
    @Transactional
    public void createPost(AddPostDto addPostDto, String loginId) {

        Member member = ensureMemberExists(loginId);

        Post addPost = Post.builder()
                .title(addPostDto.getTitle())
                .content(addPostDto.getContent())
                .category(addPostDto.getCategory())
                .member(member)
                .likeCount(0L)
                .commentCount(0L)
                .comments(new ArrayList<>())
                .build();

        postRepository.save(addPost);
    }

    // 하나의 게시글 조회 - 댓글 보임
    @Transactional
    public OnePostDetailDto getOnePost(Long postId) {

        Post post = ensurePostExists(postId);
        return OnePostDetailDto.toDto(post);
    }

    // 전체 게시글 조회 - 게시판 페이지
    @Transactional
    public List<PostDetailDto> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(PostDetailDto::toDto)
                .collect(Collectors.toList());
    }

    // 회원이 작성한 게시글들 조회
    @Transactional(readOnly = true)
    public List<PostDetailDto> getMemberPost(String loginId) {
        return postRepository
                .findAllByMember_loginId(loginId)
                .stream()
                .map((Object post) -> PostDetailDto.toDto((Post) post))
                .collect(Collectors.toList());
    }

    // 게시글 수정
    @Transactional
    public void updatePost(Long postId, String loginId, UpdatePostDto updateDto) {

        Post post = ensurePostExists(postId);
        validatePostOwner(post, loginId);

        post.setTitle(updateDto.getTitle());
        post.setContent(updateDto.getContent());
        post.setCategory(updateDto.getCategory());

        postRepository.save(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, String loginId) {

        Post post = ensurePostExists(postId);
        validatePostOwner(post, loginId);

        postRepository.delete(post);
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

    // 게시글 작성자인지 검증
    private void validatePostOwner(Post post, String loginId) {
        if (!post.getMember().getLoginId().equals(loginId)) {
            throw YouthException.from(ErrorCode.MEMBER_NOT_AUTHORIZED);
        }
    }
}