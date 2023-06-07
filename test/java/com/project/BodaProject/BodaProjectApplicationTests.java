package com.project.BodaProject;

import com.project.BodaProject.api.ExhibitionDtoApi;
//import com.project.BodaProject.repository.ExhibitionAllRepository;
import com.project.BodaProject.domain.exhibition.ExhibitionToday;
import com.project.BodaProject.dto.BlogDto;
import com.project.BodaProject.dto.ExhibitionDto;
import com.project.BodaProject.repository.ExhibitionTodayRepository;
import com.project.BodaProject.util.NaverBlogSearchApiUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class BodaProjectApplicationTests {
	@Autowired
	private ExhibitionDtoApi exhibitionApi;
	@Autowired
	private ExhibitionTodayRepository exhibitionTodayRepository;
	@Autowired
	public NaverBlogSearchApiUtil naverBlogSearchApiUtil;
//	@Autowired
//	private ExhibitionAllRepository exhibitionAllRepository;
//
//	@Test
//	void dataAppendAll() throws IOException {
//		exhibitionApi.ExhibitionApi();
//	}

	@Test
	void dataAppendToday() throws IOException {
		exhibitionApi.ExhibitionApiToday();
	}
}
