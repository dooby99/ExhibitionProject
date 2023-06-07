package com.project.BodaProject.dto.naver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageRes {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverItems> items;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class NaverItems {
        private String title;
        private String link;
        private String category;
        private String thumbnail;
        private String sizeheight;
        private String sizewidth;

    }
}