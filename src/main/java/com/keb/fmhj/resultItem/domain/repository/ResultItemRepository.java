package com.keb.fmhj.resultItem.domain.repository;

import com.keb.fmhj.resultItem.domain.ResultItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultItemRepository extends JpaRepository<ResultItem, Long> {

}
