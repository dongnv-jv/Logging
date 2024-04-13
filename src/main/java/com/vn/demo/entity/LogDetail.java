package com.vn.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "log-detail")
@AllArgsConstructor
public class LogDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private Long logId;
    private String field;
    private String oldValue;
    private String newValue;
    private LocalDateTime createdDate;
}
