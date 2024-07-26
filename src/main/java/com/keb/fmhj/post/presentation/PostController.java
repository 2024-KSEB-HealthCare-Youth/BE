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
    @Operation(summary = "Get all posts", description = "Retrieves all posts.")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    /**
     * 게시글 조회
     */

    @GetMapping("/post")
    @Operation(summary = "Get posts by loginId", description = "Retrieves posts for the logged in user.")
    public ResponseEntity<List<PostDTO>> getPostByLoginId(@RequestParam("loginId") String loginId) {
        List<PostDTO> posts = postService.getPostByLoginId(loginId);
        return ResponseEntity.ok(posts);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable("id") Long id, @RequestBody PostDTO postDTO, @RequestParam("loginId") String loginId) {
        PostDTO updatedPost = postService.updatePost(id, postDTO, loginId);
        return ResponseEntity.ok(updatedPost);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete post by ID", description = "Deletes a post by its ID for the logged in user.")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id, @RequestParam("loginId") String loginId) {
        postService.deletePost(id, loginId);
        return ResponseEntity.noContent().build();
    }
}