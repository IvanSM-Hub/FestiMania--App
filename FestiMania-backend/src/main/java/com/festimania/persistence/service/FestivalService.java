package com.festimania.persistence.service;

import com.festimania.entities.dto.FestivalCompleteDto;
import com.festimania.entities.dto.FestivalDto;
import com.festimania.exceptions.AttributeException;

import java.util.List;

public interface FestivalService {

    // Finds
    List<FestivalCompleteDto> findAllFestivals();
    FestivalCompleteDto findFestivalsById(String id);
    List<FestivalCompleteDto> findFestivalsByName(String name);
    List<FestivalCompleteDto> findFestivalsByDate(String date);
    List<FestivalCompleteDto> findFestivalsByGenre(String genre) throws AttributeException;

    // Create
    FestivalCompleteDto createFestival(FestivalDto festivalDto) throws AttributeException;

    // Alter Festival
    FestivalCompleteDto alterFestival(String id, FestivalDto updateFestival) throws AttributeException;

    // Delete Festival
    boolean deleteFestival(String id);

}
