package com.project.BodaProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExhibitionDto {
    private Long id;
    private String startDate;
    private String endDate;
    private String place;
    private String subject;
    private String[] places;
}