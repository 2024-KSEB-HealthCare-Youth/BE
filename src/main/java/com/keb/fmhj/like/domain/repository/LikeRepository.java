package com.keb.fmhj.like.domain.repository;

import com.keb.fmhj.like.domain.Like;
import com.keb.fmhj.member.domain.Member;
import com.keb.fmhj.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByMemberAndPost(Member member, Post post);
}
