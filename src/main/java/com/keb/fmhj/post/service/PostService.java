package com.keb.fmhj.post.service;

import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.post.domain.Category;
import com.keb.fmhj.post.domain.Post;
import com.keb.fmhj.post.domain.repository.PostRepository;
import com.keb.fmhj.post.domain.dto.request.PostDTO;
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

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<PostDTO> getPostById(Long id) {
        return postRepository.findById(id)
                .map(this::convertToDto);
    }

    public Post updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> YouthException.from(ErrorCode.POST_NOT_FOUND));

        // 로그인한 사용자가 이 게시글의 작성자인지 확인
        if (!post.getMember().getLoginId().equals(postDTO.getMember().getLoginId())) {
            throw YouthException.from(ErrorCode.USER_NOT_AUTHORIZED);
        }

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setViewCount(postDTO.getViewCount());
        post.setCategory(Category.valueOf(postDTO.getCategory()));
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private PostDTO convertToDto(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setLikeCount(post.getLikeCount());
        postDTO.setCommentCount(post.getCommentCount());
        postDTO.setViewCount(post.getViewCount());
        postDTO.setCategory(post.getCategory().name());
        postDTO.setMember(post.getMember());
        return postDTO;
    }
}