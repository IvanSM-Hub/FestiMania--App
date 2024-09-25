package com.festimania.persistence.service;

import com.festimania.entities.Festival;
import com.festimania.utils.enums.GenreEnum;

import java.util.Date;
import java.util.List;

public interface FestivalService {

    // Finds
    List<Festival> findAllFestivals();
    List<Festival> findFestivalsByName(String name);
    List<Festival> findFestivalsByDate(Date date);
    List<Festival> findFestivalsByGenre(GenreEnum genre);

    // Create
    boolean createFestival(Festival festival);

    // Alter Festival
    boolean alterFestival();

}
