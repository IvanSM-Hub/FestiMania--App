package com.festimania.persistence.service;

import com.festimania.entities.dto.FestivalCompleteDto;
import com.festimania.entities.dto.FestivalDto;
import com.festimania.exceptions.AttributeException;

import java.time.LocalDate;
import java.util.List;

public interface FestivalService {

    // Finds
    List<FestivalCompleteDto> findAllFestivals();
    List<FestivalCompleteDto> findFestivalsById(String id);
    List<FestivalCompleteDto> findFestivalsByName(String name);
    List<FestivalCompleteDto> findFestivalsByDate(LocalDate date);
    List<FestivalCompleteDto> findFestivalsByGenre(String genre);

    // Create
    FestivalCompleteDto createFestival(FestivalDto festivalDto) throws AttributeException;

    // Alter Festival
    FestivalCompleteDto alterFestival();
    FestivalCompleteDto addArtistToFestival(List<String> artistsIdList, String idFestival);


}
