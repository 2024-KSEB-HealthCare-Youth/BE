package com.keb.fmhj.post.presentation;

import com.keb.fmhj.post.domain.Post;
import com.keb.fmhj.post.domain.dto.request.PostDTO;
import com.keb.fmhj.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    /**
     * 게시글 등록
     */
    @PostMapping
    @Operation(summary = "Create a new post", description = "Creates a new post with the given details.")
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostDTO postDTO, @RequestParam("loginId") String loginId) {
        Post savedPost = postService.savePost(postDTO, loginId);
        return ResponseEntity.ok(savedPost);
    }

    /**
     * 전체 게시글 조회
     */
    @GetMapping
    @Operation(summary = "Get all posts", description = "Retrieves all posts for the logged in user.")
    public ResponseEntity<List<PostDTO>> getAllPosts(@RequestParam("loginId") String loginId) {
        List<PostDTO> posts = postService.getAllPosts(loginId);
        return ResponseEntity.ok(posts);
    }

    /**
     * 게시글 조회
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get post by ID", description = "Retrieves a post by its ID for the logged in user.")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id, @RequestParam("loginId") String loginId) {
        return postService.getPostById(id, loginId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing post", description = "Updates an existing post with the given details for the logged in user.")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO, @RequestParam("loginId") String loginId) {
        Post updatedPost = postService.updatePost(id, postDTO, loginId);
        return ResponseEntity.ok(updatedPost);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete post by ID", description = "Deletes a post by its ID for the logged in user.")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @RequestParam("loginId") String loginId, @RequestParam("memberId") String memberId) {
        postService.deletePost(id, loginId);
        return ResponseEntity.noContent().build();
    }
}