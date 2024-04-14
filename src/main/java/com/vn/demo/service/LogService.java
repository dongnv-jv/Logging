package com.vn.demo.service;

import com.vn.demo.entity.LogEntity;
import com.vn.demo.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;


    public LogEntity save(LogEntity log) {
        return logRepository.save(log);
    }

    public LogEntity get(Long id) {
        return logRepository.findById(id).orElse(null);
    }
}
