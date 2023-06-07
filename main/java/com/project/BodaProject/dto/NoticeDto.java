package com.project.BodaProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NoticeDto {
    private Long no;
    private String id;
    private String writer;
    private String content;
    private LocalDateTime CreateT;

}
