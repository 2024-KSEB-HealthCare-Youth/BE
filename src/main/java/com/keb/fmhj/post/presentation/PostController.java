package com.keb.fmhj.post.presentation;

import com.keb.fmhj.post.domain.Post;
import com.keb.fmhj.post.domain.dto.request.PostDTO;
import com.keb.fmhj.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post savedPost = postService.savePost(post);
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
    @GetMapping("/{id}")
    @Operation(summary = "Get post by ID", description = "Retrieves a post by its ID.")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /**
     * 게시글 수정
     */
//    @PutMapping("/{id}")
//    @Operation(summary = "Update an existing post", description = "Updates an existing post with the given details.")
//    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
//        Post updatedPost = postService.updatePost(id, postDTO);
//        return ResponseEntity.ok(updatedPost);
//    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete post by ID", description = "Deletes a post by its ID.")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}