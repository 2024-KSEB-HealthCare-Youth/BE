package com.keb.fmhj.post.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import com.keb.fmhj.post.domain.Category;
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
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 등록
    public Post savePost(PostDTO postDTO, String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> YouthException.from(ErrorCode.USER_NOT_FOUND));

        Post post = PostDTO.toEntity(postDTO);
        post.setMember(member); // Member 객체를 Post에 설정
        return postRepository.save(post);
    }

    // 로그인한 사용자의 전체 게시글 조회
    public List<PostDTO> getAllPosts(String loginId) {
        return postRepository.findAllByMember_LoginId(loginId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 로그인한 사용자의 단일 게시글 조회
    public Optional<PostDTO> getPostById(Long id, String loginId) {
        return postRepository.findByIdAndMember_LoginId(id, loginId)
                .map(this::convertToDto);
    }

    // 게시글 수정
    public Post updatePost(Long id, PostDTO postDTO, String loginId) {
        Post post = postRepository.findByIdAndMember_LoginId(id, loginId)
                .orElseThrow(() -> YouthException.from(ErrorCode.POST_NOT_FOUND));

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setCategory(postDTO.getCategory());
        return postRepository.save(post);
    }

    // 게시글 삭제
    public void deletePost(Long id, String loginId) {
        Post post = postRepository.findByIdAndMember_LoginId(id, loginId)
                .orElseThrow(() -> YouthException.from(ErrorCode.POST_NOT_FOUND));

        postRepository.deleteById(id);
    }

    private PostDTO convertToDto(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setCategory(post.getCategory());
        postDTO.setMember(post.getMember());
        return postDTO;
    }
}