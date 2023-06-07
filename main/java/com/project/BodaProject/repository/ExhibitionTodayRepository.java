package com.project.BodaProject.repository;

import com.project.BodaProject.domain.exhibition.ExhibitionToday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExhibitionTodayRepository extends JpaRepository<ExhibitionToday, Long> {
    ExhibitionToday findByPlaceAndStartDateAndSubject(Object place, Object startDate, Object subject);
    ExhibitionToday findById(int id);
}