package com.keb.fmhj.result.domain.repository;

import com.keb.fmhj.result.domain.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    Result findByMemberIdAndId(Long memberId, Long resultId);

    List<Result> findAllByMemberId(Long memberId);


    @Query("SELECT r FROM Result r LEFT JOIN FETCH r.resultItems WHERE r.member.id = :memberId ORDER BY r.id DESC")
    List<Result> findResultsByMemberIdOrderByIdDesc(@Param("memberId") Long memberId);

    default Result findLatestResultByMemberId(Long memberId) {
        List<Result> results = findResultsByMemberIdOrderByIdDesc(memberId);
        return results.isEmpty() ? null : results.get(0);
    }
}
