package com.keb.fmhj.comment.service;

import com.keb.fmhj.comment.domain.Comment;
import com.keb.fmhj.comment.domain.dto.request.AddCommentDto;
import com.keb.fmhj.comment.domain.dto.request.UpdateCommentDto;
import com.keb.fmhj.comment.domain.dto.response.CommentDetailDto;
import com.keb.fmhj.comment.domain.repository.CommentRepository;
import com.keb.fmhj.global.exception.ErrorCode;
import com.keb.fmhj.global.exception.YouthException;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.member.domain.repository.MemberRepository;
import com.keb.fmhj.post.domain.Post;
import com.keb.fmhj.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    // 댓글 등록
    @Transactional
    public void createComment(AddCommentDto addDto, Long postId, String loginId) {

        Post post = ensurePostExists(postId);
        Member member = ensureMemberExists(loginId);

        Comment addComment = AddCommentDto.toEntity(addDto);
        addComment.setPost(post);
        addComment.setMember(member);

        commentRepository.save(addComment);
    }

    // 단일 댓글 조회
    @Transactional(readOnly = true)
    public CommentDetailDto getCommentDetails(Long commentId) {

        Comment comment = ensureCommentExists(commentId);
        return CommentDetailDto.toDto(comment);
    }

    // 전체 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentDetailDto> getAllCommentDetails(Long postId) {

        return commentRepository
                .findByPost_PostId(postId)
                .stream()
                .map((CommentDetailDto::toDto))
                .collect(Collectors.toList());
    }

    // 댓글 수정
    @Transactional
    public void updateComment(String loginId, Long postId, Long commentId, UpdateCommentDto updateDto) {

        Comment comment = ensureCommentExists(commentId);
        ensureCommentBelongsToPost(comment, postId);
        validateCommentOwner(comment, loginId);

        comment.setContent(updateDto.getContent());

        commentRepository.save(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, String loginId) {

        Comment comment = ensureCommentExists(commentId);
        validateCommentOwner(comment, loginId);

        commentRepository.delete(comment);
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

    // 댓글 존재 유무 검증
    private Comment ensureCommentExists(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> YouthException.from(ErrorCode.COMMENT_NOT_FOUND));
    }

    // 댓글이 해당 게시글에 속하는지 검증
    private void ensureCommentBelongsToPost(Comment comment, Long postId) {
        if(!postId.equals(comment.getPost().getPostId())) {
            throw YouthException.from(ErrorCode.POST_NOT_FOUND);
        }
    }

    // 댓글 작성자인지 검증
    private void validateCommentOwner(Comment comment, String loginId) {
        if (!comment.getMember().getLoginId().equals(loginId)) {
            throw YouthException.from(ErrorCode.MEMBER_NOT_AUTHORIZED);
        }
    }
}
