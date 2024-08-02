package com.keb.fmhj.like.domain.repository;

import com.keb.fmhj.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByMemberIdAndPost_PostId(long id, long postId);
}
