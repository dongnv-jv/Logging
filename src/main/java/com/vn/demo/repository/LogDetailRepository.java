package com.vn.demo.repository;

import com.vn.demo.entity.LogDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogDetailRepository extends JpaRepository<LogDetail, Long> {
    List<LogDetail> findByLogId(Long logId);
}
