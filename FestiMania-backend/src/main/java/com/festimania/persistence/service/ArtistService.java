package com.festimania.persistence.service;

import com.festimania.entities.Artist;
import com.festimania.entities.dto.ArtistDto;
import com.festimania.exceptions.AttributeException;
import com.festimania.utils.enums.GenreEnum;

import java.util.List;

public interface ArtistService {

    // finds
    List<Artist> findAllArtists();
    List<Artist> findFestivalsByName(String name);
    List<Artist> findFestivalsByGenre(GenreEnum genre);
    Artist findByName(String name);
    Artist findById(String id);

    // Create
    Artist createArtist(ArtistDto newArtist) throws AttributeException;

    // Alter
    boolean alterArtist(ArtistDto changeArtist);

    // Delete
    boolean deleteArtist(String idArtist);

}
