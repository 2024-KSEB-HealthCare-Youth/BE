package com.keb.fmhj.result.domain.repository;

import com.keb.fmhj.result.domain.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    Result findByMemberIdAndId(Long memberId, Long resultId);
    List<Result> findAllByMemberId(Long memberId);
}
