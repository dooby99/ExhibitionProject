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
public class BoardDto {

    private Long no;
    private String name;
    private String title;
    private String content;
    private LocalDateTime CreateT;
    private LocalDateTime ModifyT;
    private Long view;


}
