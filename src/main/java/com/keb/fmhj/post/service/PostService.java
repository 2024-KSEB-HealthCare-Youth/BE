package com.keb.fmhj.post.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import com.keb.fmhj.post.domain.Post;
import com.keb.fmhj.post.domain.dto.request.PostDTO;
import com.keb.fmhj.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 등록
    public Post savePost(PostDTO postDTO, String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> YouthException.from(ErrorCode.USER_NOT_FOUND));

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .category(postDTO.getCategory())
                .member(member)
                .build();
        return postRepository.save(post);
    }

    // 전체 게시글 조회
    @Transactional
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 단일 게시글 조회
    @Transactional
    public List<PostDTO> getPostByLoginId(String loginId) {
        return postRepository.findByMember_LoginId(loginId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    // 게시글 수정
    @Transactional
    public PostDTO updatePost(Long id, PostDTO postDTO, String loginId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> YouthException.from(ErrorCode.POST_NOT_FOUND));

        // 로그인한 사용자가 이 게시글의 작성자인지 확인
        if (!post.getMember().getLoginId().equals(loginId)) {
            throw YouthException.from(ErrorCode.USER_NOT_AUTHORIZED);
        }

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setCategory(postDTO.getCategory());

        Post updatedPost = postRepository.save(post);

        // DTO로 변환하여 반환
        return PostDTO.fromEntity(updatedPost);
    }


    // 게시글 삭제
    @Transactional
    public void deletePost(Long id, String loginId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> YouthException.from(ErrorCode.POST_NOT_FOUND));

        // 로그인한 사용자가 이 게시글의 작성자인지 확인
        if (!post.getMember().getLoginId().equals(loginId)) {
            throw YouthException.from(ErrorCode.USER_NOT_AUTHORIZED);
        }

        postRepository.deleteById(id);
    }

    private PostDTO convertToDto(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setCategory(post.getCategory());
        return postDTO;
    }
}