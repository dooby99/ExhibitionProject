package com.project.BodaProject.config;

import com.project.BodaProject.api.ExhibitionDtoApi;
import com.project.BodaProject.domain.exhibition.ExhibitionToday;
import com.project.BodaProject.repository.ExhibitionTodayRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Configuration
public class ExhibitionConfig {
    @Bean
    public ExhibitionDtoApi exhibitionApiAll() {
        return new ExhibitionDtoApi();
    }
}
