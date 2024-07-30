package com.keb.fmhj.post.presentation;

import com.keb.fmhj.auth.utils.AccessTokenUtils;
import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.response.ApiResponse;
import com.keb.fmhj.post.domain.dto.request.AddPostDto;
import com.keb.fmhj.post.domain.dto.request.UpdatePostDto;
import com.keb.fmhj.post.domain.dto.response.PostDetailDto;
import com.keb.fmhj.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
    public ApiResponse<Void> createPost(@Validated @RequestBody AddPostDto addPostDto) {

        String loginId = AccessTokenUtils.isPermission();
        postService.createPost(addPostDto, loginId);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @GetMapping
    @Operation(summary = "전체 게시글 조회 API", description = "전체 게시글을 조회합니다.")
    public ApiResponse<PostDetailDto> getAllPosts() {

        List<PostDetailDto> posts = postService.getAllPosts();
        return new ApiResponse<>(posts);
    }

    @GetMapping("/me")
    @Operation(summary = "내 게시글들 조회 API", description = "내가 작성한 게시글들에 대해 조회합니다.")
    public ApiResponse<PostDetailDto> getMemberPosts() {

        String loginId = AccessTokenUtils.isPermission();
        List<PostDetailDto> posts = postService.getMemberPost(loginId);
        return new ApiResponse<>(posts);
    }

    @PutMapping("/me")
    @Operation(summary = "게시글 수정 API", description = "게시글을 수정합니다.")
    public ApiResponse<PostDetailDto> updatePost(@RequestParam Long postId,
                                                 @Validated @RequestBody UpdatePostDto updatePostDto) {

        String loginId = AccessTokenUtils.isPermission();
        postService.updatePost(postId, loginId, updatePostDto);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }

    @DeleteMapping("/me")
    @Operation(summary = "게시글 삭제 API", description = "게시글을 삭제합니다.")
    public ApiResponse<PostDetailDto> deletePost(@RequestParam Long postId) {

        String loginId = AccessTokenUtils.isPermission();
        postService.deletePost(postId, loginId);
        return new ApiResponse<>(ErrorCode.REQUEST_OK);
    }
}
