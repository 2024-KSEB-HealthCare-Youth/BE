package com.keb.fmhj.post.domain.repository;

import com.keb.fmhj.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByMember_LoginId(String loginId);
    Optional<Post> findByIdAndMember_LoginId(Long id, String loginId);
}