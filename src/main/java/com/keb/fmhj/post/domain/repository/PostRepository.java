package com.keb.fmhj.post.domain.repository;

import com.keb.fmhj.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByMember_LoginId(String loginId);
}