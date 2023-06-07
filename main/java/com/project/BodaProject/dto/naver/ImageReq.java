package com.project.BodaProject.dto.naver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ImageReq {

    private String query;

    @Builder.Default
    private int display = 1;
    @Builder.Default
    private int start = 1;
    @Builder.Default
    private String sort = "sim";
    @Builder.Default
    private String filter = "all";

    public MultiValueMap<String, String> getQueryParamsMap(){
        var map = new LinkedMultiValueMap<String, String>();
        map.add("query",query);
        map.add("display",String.valueOf(display));
        map.add("start",String.valueOf(start));
        map.add("sort",sort);
        map.add("filter",filter);

        return map;
    }

}