package com.keb.fmhj.post.presentation;

import com.keb.fmhj.post.domain.Post;
import com.keb.fmhj.post.domain.dto.request.PostDTO;
import com.keb.fmhj.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Tag(name = "post", description = "게시판 관련 API")
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(summary = "게시글 등록 API", description = "게시글을 등록합니다.")
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostDTO postDTO, @RequestParam("loginId") String loginId) {
        Post savedPost = postService.savePost(postDTO, loginId);
        return ResponseEntity.ok(savedPost);
    }

    @GetMapping
    @Operation(summary = "전체 게시글 조회 API", description = "전체 게시글을 조회합니다.")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/post")
    @Operation(summary = "단일 게시글 조회 API", description = "회원이 작성한 게시글에 대해 조회합니다.")
    public ResponseEntity<List<PostDTO>> getPostByLoginId(@RequestParam("loginId") String loginId) {
        List<PostDTO> posts = postService.getPostByLoginId(loginId);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{id}")
    @Operation(summary = "게시글 수정 API", description = "게시글을 수정합니다.")
    public ResponseEntity<PostDTO> updatePost(@PathVariable("id") Long id, @RequestBody PostDTO postDTO, @RequestParam("loginId") String loginId) {
        PostDTO updatedPost = postService.updatePost(id, postDTO, loginId);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "게시글 삭제 API", description = "게시글을 삭제합니다.")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id, @RequestParam("loginId") String loginId) {
        postService.deletePost(id, loginId);
        return ResponseEntity.noContent().build();
    }
}
