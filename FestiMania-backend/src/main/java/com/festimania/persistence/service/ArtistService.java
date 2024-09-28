package com.festimania.persistence.service;

import com.festimania.entities.Artist;
import com.festimania.entities.dto.ArtistDto;
import com.festimania.exceptions.AttributeException;

import java.util.List;

public interface ArtistService {

    // finds
    List<Artist> findAllArtists();
    List<Artist> findArtistsByName(String name);
    List<Artist> findArtistsByGenre(String genre) throws AttributeException;
    Artist findById(String id);

    // Create
    Artist createArtist(ArtistDto newArtist) throws AttributeException;

    // Alter
    Artist updateArtist(String id, ArtistDto updateArtist) throws AttributeException;

    // Delete
    boolean deleteArtist(String id);

}
