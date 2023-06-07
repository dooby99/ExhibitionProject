package com.project.BodaProject.controller;

import com.project.BodaProject.domain.exhibition.ExhibitionToday;
import com.project.BodaProject.dto.BlogDto;
import com.project.BodaProject.dto.naver.ImageReq;
import com.project.BodaProject.repository.ExhibitionTodayRepository;
import com.project.BodaProject.service.NaverBlogImgService;
import com.project.BodaProject.util.NaverBlogSearchApiUtil;
import com.project.BodaProject.util.NaverImgSearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class MapController {
    @Autowired
    private ExhibitionTodayRepository exhibitionTodayRepository;
    @Autowired
    private NaverBlogSearchApiUtil naverBlogSearchApiUtil;
    @Autowired
    private NaverImgSearchUtil naverImgSearchUtil;
    @Autowired
    private NaverBlogImgService naverBlogImgService;

    @RequestMapping("/exhibition")
    public String exhibition(@RequestParam("subject") Object subject,
                             @RequestParam("startDate") Object startDate,
                             @RequestParam("endDate") Object endDate,
                             @RequestParam("place") Object place,
                             Model model) throws IOException {

        ExhibitionToday exhibitionToday = exhibitionTodayRepository.findByPlaceAndStartDateAndSubject(place, startDate, subject);

        List<BlogDto> blogList = naverBlogSearchApiUtil.getWord(exhibitionToday);

        String firstLink;
        String firstTitle;
        String firstBloggername;
        String firstPostdate;
        String firstImgUrl;

        firstLink = blogList.get(0).getLink();
        firstTitle = blogList.get(0).getTitle();
        firstBloggername = blogList.get(0).getBloggername();
        firstPostdate = blogList.get(0).getPostdate();
        firstImgUrl = naverBlogImgService.getThumbnailUrl(blogList.get(0).getLink());


        String secondLink;
        String secondTitle;
        String secondBloggername;
        String secondPostdate;
        String secondImgUrl;

        secondLink = blogList.get(1).getLink();
        secondTitle = blogList.get(1).getTitle();
        secondBloggername = blogList.get(1).getBloggername();
        secondPostdate = blogList.get(1).getPostdate();
        secondImgUrl = naverBlogImgService.getThumbnailUrl(blogList.get(1).getLink());

        ImageReq req = ImageReq.builder().query(subject.toString()).build();
        String image;

        if (naverImgSearchUtil.imageSearch(req).getItems() == null
        || naverImgSearchUtil.imageSearch(req).getItems().isEmpty()) {
            image = null;
        } else {
            image = naverImgSearchUtil.imageSearch(req).getItems().get(0).getLink();
        }

        String[] searchKey = exhibitionToday.getPlaces();

        model.addAttribute("searchKey", searchKey);

        model.addAttribute("image", image);
        model.addAttribute("subject", subject);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("place", place);

        model.addAttribute("firstLink", firstLink);
        model.addAttribute("firstTitle", firstTitle);
        model.addAttribute("firstBloggername", firstBloggername);
        model.addAttribute("firstPostdate", firstPostdate);
        model.addAttribute("firstImgUrl", firstImgUrl);

        model.addAttribute("secondLink", secondLink);
        model.addAttribute("secondTitle", secondTitle);
        model.addAttribute("secondBloggername", secondBloggername);
        model.addAttribute("secondPostdate", secondPostdate);
        model.addAttribute("secondImgUrl", secondImgUrl);


        return "exhibition";
    }

    @RequestMapping("/main")
    public String main(Model model) {
        ImageReq req;
        String firstSubject;
        String firstSDate;
        String firstEDate;
        String firstPlace;
        String firstImg;

        ExhibitionToday exhibition = exhibitionTodayRepository.findById(1);
        firstSubject = exhibition.getSubject();
        firstSDate = exhibition.getStartDate();
        firstEDate = exhibition.getEndDate();
        firstPlace = exhibition.getPlace();
        req = ImageReq.builder().query(exhibition.getSubject()).build();
        firstImg = naverImgSearchUtil.imageSearch(req).getItems().get(0).getThumbnail();

        String secondSubject;
        String secondSDate;
        String secondEDate;
        String secondPlace;
        String secondImg;

        exhibition = exhibitionTodayRepository.findById(2);
        secondSubject = exhibition.getSubject();
        secondSDate = exhibition.getStartDate();
        secondEDate = exhibition.getEndDate();
        secondPlace = exhibition.getPlace();
        req = ImageReq.builder().query(exhibition.getSubject()).build();
        secondImg = naverImgSearchUtil.imageSearch(req).getItems().get(0).getThumbnail();

        String thirdSubject;
        String thirdSDate;
        String thirdEDate;
        String thirdPlace;
        String thirdImg;

        exhibition = exhibitionTodayRepository.findById(3);
        thirdSubject = exhibition.getSubject();
        thirdSDate = exhibition.getStartDate();
        thirdEDate = exhibition.getEndDate();
        thirdPlace = exhibition.getPlace();
        req = ImageReq.builder().query(exhibition.getSubject()).build();
        thirdImg = naverImgSearchUtil.imageSearch(req).getItems().get(0).getThumbnail();

        model.addAttribute("firstSubject", firstSubject);
        model.addAttribute("firstSDate", firstSDate);
        model.addAttribute("firstEDate", firstEDate);
        model.addAttribute("firstPlace", firstPlace);
        model.addAttribute("firstImg", firstImg);

        model.addAttribute("secondSubject", secondSubject);
        model.addAttribute("secondSDate", secondSDate);
        model.addAttribute("secondEDate", secondEDate);
        model.addAttribute("secondPlace", secondPlace);
        model.addAttribute("secondImg", secondImg);

        model.addAttribute("thirdSubject", thirdSubject);
        model.addAttribute("thirdSDate", thirdSDate);
        model.addAttribute("thirdEDate", thirdEDate);
        model.addAttribute("thirdPlace", thirdPlace);
        model.addAttribute("thirdImg", thirdImg);

        return "main";
    }

    @RequestMapping("/search-page")
    public String searchPage(@RequestParam("query") String query, Model model) {

        String[] queryList = query.split(" ");

        List<ExhibitionToday> exhibitionList = new ArrayList<>();
        List<ExhibitionToday> tempExhibitionList = exhibitionTodayRepository.findAll();

        for (ExhibitionToday tempExhibition : tempExhibitionList) {
            for (String keyword : queryList) {
                if (tempExhibition.getSubject().contains(keyword) && !exhibitionList.contains(tempExhibition)) {
                    exhibitionList.add(tempExhibition);
                } else if (tempExhibition.getPlace().contains(keyword) && !exhibitionList.contains(tempExhibition)) {
                    exhibitionList.add(tempExhibition);
                }
            }
        }

        List<String> thumbnailList = new ArrayList<>();
        for (ExhibitionToday exhibition : exhibitionList) {
            ImageReq req = ImageReq.builder().query(exhibition.getSubject()).build();
            String thumbnail;
            if (naverImgSearchUtil.imageSearch(req).getItems().isEmpty()
                    || naverImgSearchUtil.imageSearch(req).getItems() == null) {
                thumbnailList.add("");
            } else {
                thumbnail = naverImgSearchUtil.imageSearch(req).getItems().get(0).getLink();
                thumbnailList.add(thumbnail);
            }
        }

        if (exhibitionList == null || exhibitionList.isEmpty()) {
            model.addAttribute("searchKeyword", query);
            model.addAttribute("searchCount", exhibitionList.size());
            model.addAttribute("exhibitionList", exhibitionList);
            model.addAttribute("thumbnailList", thumbnailList);
        } else {
            model.addAttribute("searchKeyword", query);
            model.addAttribute("searchCount", exhibitionList.size());
            model.addAttribute("exhibitionList", exhibitionList);
            model.addAttribute("thumbnailList", thumbnailList);
        }

        return "searchPage";
    }

    @RequestMapping("/my-page")
    public String myPage() {
        return "myPage";
    }

    @RequestMapping("/notice")
    public String notice() {
        return "notice";
    }
}