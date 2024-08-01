package com.keb.fmhj.post.domain.repository;

import com.keb.fmhj.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Collection<Object> findAllByMember_loginId(String loginId);

    Optional<Post> findByPostId(Long postId);
}