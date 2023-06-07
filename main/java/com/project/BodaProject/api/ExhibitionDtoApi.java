package com.project.BodaProject.api;

//import com.project.BodaProject.domain.exhibition.ExhibitionAll;
import com.project.BodaProject.domain.exhibition.ExhibitionToday;
import com.project.BodaProject.dto.ExhibitionDto;
//import com.project.BodaProject.repository.ExhibitionAllRepository;
import com.project.BodaProject.repository.ExhibitionTodayRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
//전체 DB
public class ExhibitionDtoApi extends ExhibitionDto {
//    @Autowired
//    private ExhibitionAllRepository exhibitionAllRepository;
    @Autowired
    private ExhibitionTodayRepository exhibitionTodayRepository;

//    public void RoadApi(String date) throws IOException {
//        JSONArray jsonArray = null;
//        JSONObject jsonObject = null;
//
//        List<ExhibitionAll> parsingData = new ArrayList<>();
//            /*
//                공공데이터포털 대구광역시_전시정보 API 사용
//                https://dgfc.or.kr/ajax/event/list.json?event_gubun=DP&start_date=date
//                출처
//            */
//        StringBuilder requestUrl = new StringBuilder("https://dgfca.or.kr/ajax/event/list.json?event_gubun=DP&start_date=");
//        requestUrl.append(URLEncoder.encode(date, "UTF-8"));
//
//        System.out.println("request result : " + requestUrl);
//        URL url = new URL(requestUrl.toString());
//
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        System.out.println("Response code: " + conn.getResponseCode() + "\n");
//
//        BufferedReader rd;
//        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {           //응답코드 읽기
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));      //정상적으로 값 읽기
//        } else {
//            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));      //에러값 읽기
//        }
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//        rd.close();
//        conn.disconnect();
//        String result = sb.toString();
//
//        //테스트용
////        System.out.println("response check : ");
////        System.out.println(result);
//
//        try {
//            JSONParser parser = new JSONParser();
//            Object obj = parser.parse(result);
//
//            //obj 타입이 JSONArray나 JSONObject인지 여부 검사
//            if (obj instanceof JSONArray) {
//                jsonArray = (JSONArray) obj;
//                //JSONObject로 변환
//                for (Object o : jsonArray) {
//                    jsonObject = (JSONObject) o;
//                    ExhibitionAll exhibition = new ExhibitionAll();
//                    //필요한 정보 파싱
//                    exhibition.setStartDate((String) jsonObject.get("start_date"));
//                    exhibition.setEndDate((String) jsonObject.get("end_date"));
//                    exhibition.setSubject((String) jsonObject.get("subject"));
//                    exhibition.setPlace((String) jsonObject.get("place"));
//                    exhibition.setPlaces(exhibition.getPlace().split(" "));
//
//                    //중복 확인을 위해 비교대상 참조
//                    var saveData = exhibitionAllRepository.findByPlaceAndStartDateAndSubject(
//                            exhibition.getPlace(), exhibition.getStartDate(), exhibition.getSubject());
//
//                    //테스트용
////                    log.info("saveData : {}", saveData);
////                    log.info("exhibition : {}", exhibition);
//
//                    //DB에서 찾은 데이터와 파싱한 데이터가 같은 지 비교 후, 중복이 아니면 parsongData에 저장
//                    if (saveData == null ||
//                            (!saveData.getStartDate().equals(exhibition.getStartDate())
//                                    && !saveData.getEndDate().equals(exhibition.getEndDate())
//                                    && !saveData.getSubject().equals(exhibition.getSubject())
//                                    && !saveData.getPlace().equals(exhibition.getPlace()))
//                    ) {
//                        String[] encodedPlaces = exhibition.getPlaces();
//                        String[] decodedPlaces = new String[encodedPlaces.length];
//                        for (int i = 0; i < encodedPlaces.length; i++) {
//                            decodedPlaces[i] = URLDecoder.decode(encodedPlaces[i], "UTF-8");
//                        }
//                        exhibition.setPlaces(decodedPlaces);
//
//                        //테스트용
////                        for (String place : decodedPlaces) {
////                            System.out.println(place);
////                        }
//
//                        parsingData.add(exhibition);
//                    }
//                }
//            } else if (obj instanceof JSONObject) {
//                jsonObject = (JSONObject) obj;
//                ExhibitionAll exhibition = new ExhibitionAll();
//                exhibition.setStartDate((String) jsonObject.get("start_date"));
//                exhibition.setEndDate((String) jsonObject.get("end_date"));
//                exhibition.setSubject((String) jsonObject.get("subject"));
//                exhibition.setPlace((String) jsonObject.get("place"));
//                exhibition.setPlaces(exhibition.getPlace().split(" "));
//
//                var saveData = exhibitionAllRepository.findByPlaceAndStartDateAndSubject(
//                        exhibition.getPlace(), exhibition.getStartDate(), exhibition.getSubject());
//
//                //테스트용
////                log.info("saveData : {}", saveData);
////                log.info("exhibition : {}", exhibition);
//
//                if (saveData == null ||
//                        (!saveData.getStartDate().equals(exhibition.getStartDate())
//                                && !saveData.getEndDate().equals(exhibition.getEndDate())
//                                && !saveData.getSubject().equals(exhibition.getSubject())
//                                && !saveData.getPlace().equals(exhibition.getPlace()))
//                ) {
//                    String[] encodedPlaces = exhibition.getPlaces();
//                    String[] decodedPlaces = new String[encodedPlaces.length];
//                    for (int i = 0; i < encodedPlaces.length; i++) {
//                        decodedPlaces[i] = URLDecoder.decode(encodedPlaces[i], "UTF-8");
//                    }
//                    exhibition.setPlaces(decodedPlaces);
//
//                    //테스트용
////                    for (String place : decodedPlaces) {
////                        System.out.println(place);
////                    }
//
//                    parsingData.add(exhibition);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        for (ExhibitionAll exhibition : parsingData) {
//            //테스트용
////            System.out.println("Start Date: " + exhibition.getStartDate());
////            System.out.println("End Date: " + exhibition.getEndDate());
////            System.out.println("Subject: " + exhibition.getSubject());
////            System.out.println("Place: " + exhibition.getPlace());
////            System.out.println();
//
//            //테스트용
////            System.out.println("Places[0] : " + exhibition.getPlaces()[0]);
//
//            exhibitionAllRepository.save(exhibition);      //DB에 가공된 데이터 저장
//        }
//
//        //현재 날짜에서 마감된 전시 삭제...
//    }

//    @Transactional
//    public void ExhibitionApi() throws IOException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");       //추출할 날짜 형식(yyyy-MM-dd)
//        String date = dateFormat.format(System.currentTimeMillis());        //오늘 날짜
//        RoadApi(date);
//
//        String baseDate1 = "2023-0";
//        for (int i=4; i<10; i++) {
//            date = baseDate1 + i;
//            RoadApi(date);
//        }
//
//        String baseDate2 = "2023-1";
//        for (int i=0; i<3; i++) {
//            date = baseDate2 + i;
//            RoadApi(date);
//        }
//    }

    @Transactional
    public void ExhibitionApiToday() throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");       //추출할 날짜 형식(yyyy-MM-dd)
        String date = dateFormat.format(System.currentTimeMillis());        //오늘 날짜

        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        List<ExhibitionToday> parsingData = new ArrayList<>();
        /*
            공공데이터포털 대구광역시_전시정보 API 사용
            https://dgfc.or.kr/ajax/event/list.json?event_gubun=DP&start_date=date
            출처
        */
        StringBuilder requestUrl = new StringBuilder("https://dgfca.or.kr/ajax/event/list.json?event_gubun=DP&start_date=");
        requestUrl.append(URLEncoder.encode(date, "UTF-8"));

        System.out.println("request result : " + requestUrl);
        URL url = new URL(requestUrl.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        System.out.println("Response code: " + conn.getResponseCode() + "\n");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {           //응답코드 읽기
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));      //정상적으로 값 읽기
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));      //에러값 읽기
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String result = sb.toString();

        //테스트용
//        System.out.println("response check : ");
//        System.out.println(result);

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(result);

            //obj 타입이 JSONArray나 JSONObject인지 여부 검사
            if (obj instanceof JSONArray) {
                jsonArray = (JSONArray) obj;
                //JSONObject로 변환
                for (Object o : jsonArray) {
                    jsonObject = (JSONObject) o;
                    ExhibitionToday exhibition = new ExhibitionToday();
                    //필요한 정보 파싱
                    exhibition.setStartDate((String) jsonObject.get("start_date"));
                    exhibition.setEndDate((String) jsonObject.get("end_date"));
                    exhibition.setSubject((String) jsonObject.get("subject"));
                    exhibition.setPlace((String) jsonObject.get("place"));
                    exhibition.setPlaces(exhibition.getPlace().replaceAll("[^a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]", " ").split(" "));

                    var saveData = exhibitionTodayRepository.findByPlaceAndStartDateAndSubject(
                            exhibition.getPlace(), exhibition.getStartDate(), exhibition.getSubject());

                    //테스트용
//                log.info("saveData : {}", saveData);
//                log.info("exhibition : {}", exhibition);

                    if (saveData == null ||
                            (!saveData.getStartDate().equals(exhibition.getStartDate())
                                    && !saveData.getEndDate().equals(exhibition.getEndDate())
                                    && !saveData.getSubject().equals(exhibition.getSubject())
                                    && !saveData.getPlace().equals(exhibition.getPlace()))
                    ) {
                        String[] encodedPlaces = exhibition.getPlaces();
                        String[] decodedPlaces = new String[encodedPlaces.length];
                        for (int i = 0; i < encodedPlaces.length; i++) {
                            decodedPlaces[i] = URLDecoder.decode(encodedPlaces[i], "UTF-8");
                        }
                        exhibition.setPlaces(decodedPlaces);

                        //테스트용
//                    for (String place : decodedPlaces) {
//                        System.out.println(place);
//                    }

                        parsingData.add(exhibition);
                    }
                }
            } else if (obj instanceof JSONObject) {
                jsonObject = (JSONObject) obj;
                ExhibitionToday exhibition = new ExhibitionToday();
                exhibition.setStartDate((String) jsonObject.get("start_date"));
                exhibition.setEndDate((String) jsonObject.get("end_date"));
                exhibition.setSubject((String) jsonObject.get("subject"));
                exhibition.setPlace((String) jsonObject.get("place"));
                exhibition.setPlaces(exhibition.getPlace().replaceAll("[^a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]", " ").split(" "));

                var saveData = exhibitionTodayRepository.findByPlaceAndStartDateAndSubject(
                        exhibition.getPlace(), exhibition.getStartDate(), exhibition.getSubject());

                //테스트용
//                log.info("saveData : {}", saveData);
//                log.info("exhibition : {}", exhibition);

                if (saveData == null ||
                        (!saveData.getStartDate().equals(exhibition.getStartDate())
                                && !saveData.getEndDate().equals(exhibition.getEndDate())
                                && !saveData.getSubject().equals(exhibition.getSubject())
                                && !saveData.getPlace().equals(exhibition.getPlace()))
                ) {
                    String[] encodedPlaces = exhibition.getPlaces();
                    String[] decodedPlaces = new String[encodedPlaces.length];
                    for (int i = 0; i < encodedPlaces.length; i++) {
                        decodedPlaces[i] = URLDecoder.decode(encodedPlaces[i], "UTF-8");
                    }
                    exhibition.setPlaces(decodedPlaces);

                    //테스트용
//                    for (String place : decodedPlaces) {
//                        System.out.println(place);
//                    }

                    parsingData.add(exhibition);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (ExhibitionToday exhibition : parsingData) {
            //테스트용
//            System.out.println("Start Date: " + exhibition.getStartDate());
//            System.out.println("End Date: " + exhibition.getEndDate());
//            System.out.println("Subject: " + exhibition.getSubject());
//            System.out.println("Place: " + exhibition.getPlace());
//            System.out.println();

            exhibitionTodayRepository.save(exhibition);      //DB에 가공된 데이터 저장
        }

        //현재 날짜에서 마감된 전시 삭제...
    }
}