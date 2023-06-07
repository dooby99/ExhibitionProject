package com.project.BodaProject.util;
//import com.project.BodaProject.domain.exhibition.ExhibitionAll;
import com.project.BodaProject.domain.exhibition.ExhibitionToday;
import com.project.BodaProject.dto.BlogDto;
import com.project.BodaProject.dto.ExhibitionDto;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;


import java.io.*;
import java.net.*;
import java.util.*;

@Component
public class NaverBlogSearchApiUtil extends BlogDto {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("Res_ko_KR_keys");
    private final static String CLIENTID = resourceBundle.getString("naverClientId"); // 애플리케이션 클라이언트 아이디
    private final static String CLIENT_SECRET = resourceBundle.getString("naverClientSecret"); //애플리케이션 클라이언트 시크릿

    public List<BlogDto> getWord(ExhibitionToday exhibitionToday) throws IOException {
        String word = null;

        //웹에 보이는 메뉴 클릭 시
        try {
            word = URLEncoder.encode(exhibitionToday.getSubject(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + word + "&sort=date";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", CLIENTID);
        requestHeaders.put("X-Naver-Client-Secret", CLIENT_SECRET);
        String responseBody = get(apiURL, requestHeaders);

//        System.out.println(responseBody);

        List<BlogDto> parsingData = new ArrayList<>();

        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        try {
            org.json.simple.parser.JSONParser parser = new JSONParser();
            JSONObject items = (JSONObject) parser.parse(responseBody);

            Object obj = items.get("items");

            //obj 타입이 JSONArray나 JSONObject인지 여부 검사
            if (obj instanceof JSONArray) {
                jsonArray = (JSONArray) obj;
                //JSONObject로 변환
                for (Object o : jsonArray) {
                    jsonObject = (JSONObject) o;
                    BlogDto blogDto = new BlogDto();
                    //필요한 정보 파싱
                    blogDto.setTitle((String) jsonObject.get("title"));
                    blogDto.setLink((String) jsonObject.get("link"));
                    blogDto.setBloggername((String) jsonObject.get("bloggername"));
                    blogDto.setPostdate((String) jsonObject.get("postdate"));

                    blogDto.setTitle(blogDto.getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));

                    parsingData.add(blogDto);
                }
            } else if (obj instanceof JSONObject) {
                jsonObject = (JSONObject) obj;
                BlogDto blogDto = new BlogDto();
                //필요한 정보 파싱
                blogDto.setTitle((String) jsonObject.get("title"));
                blogDto.setLink((String) jsonObject.get("link"));
                blogDto.setBloggername((String) jsonObject.get("bloggername"));
                blogDto.setPostdate((String) jsonObject.get("postdate"));

                blogDto.setTitle(blogDto.getTitle().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));

                parsingData.add(blogDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsingData;
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
