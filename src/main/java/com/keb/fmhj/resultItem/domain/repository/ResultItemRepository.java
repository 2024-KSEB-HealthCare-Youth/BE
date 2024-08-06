package com.keb.fmhj.resultItem.domain.repository;

import com.keb.fmhj.resultItem.domain.ResultItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultItemRepository extends JpaRepository<ResultItem, Long> {

    @Query("SELECT ri FROM ResultItem ri WHERE ri.result.id = :resultId")
    List<ResultItem> findResultItemsByResultId(@Param("resultId") Long resultId);

}
