package com.vn.demo.service;

import com.vn.demo.entity.LogDetail;
import com.vn.demo.repository.LogDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogDetailService {

    private final LogDetailRepository logDetailRepository;


    public LogDetail save(LogDetail log) {
        return logDetailRepository.save(log);
    }

    public List<LogDetail> get(Long idLog) {
        return logDetailRepository.findByLogId(idLog);
    }
}
