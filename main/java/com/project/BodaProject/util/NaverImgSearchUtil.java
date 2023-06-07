package com.project.BodaProject.util;

import com.project.BodaProject.dto.naver.ImageReq;
import com.project.BodaProject.dto.naver.ImageRes;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ResourceBundle;

@Component
public class NaverImgSearchUtil {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("Res_ko_KR_keys");
    private final static String CLIENTID = resourceBundle.getString("naverClientId"); // 애플리케이션 클라이언트 아이디
    private final static String CLIENT_SECRET = resourceBundle.getString("naverClientSecret"); //애플리케이션 클라이언트 시크릿
    private final static String imageUrl = "https://openapi.naver.com/v1/search/image";

    public ImageRes imageSearch(ImageReq req){
        var uri = UriComponentsBuilder.fromUriString(imageUrl)
                .queryParams(req.getQueryParamsMap())
                .encode()
                .build()
                .toUri();

        var reqEntity = RequestEntity.get(uri)
                .header("X-Naver-Client-Id", CLIENTID)
                .header("X-Naver-Client-Secret", CLIENT_SECRET)
                .build();

        var responseEntity =
                new RestTemplate().exchange(reqEntity,
                        new ParameterizedTypeReference<ImageRes>() {
                        });

        return responseEntity.getBody();
    }
}
