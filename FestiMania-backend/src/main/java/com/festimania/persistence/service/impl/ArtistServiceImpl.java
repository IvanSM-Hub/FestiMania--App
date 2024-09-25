package com.festimania.persistence.service.impl;

import com.festimania.entities.Artist;
import com.festimania.entities.dto.ArtistDto;
import com.festimania.exceptions.AttributeException;
import com.festimania.persistence.repositories.ArtistRepository;
import com.festimania.persistence.service.ArtistService;
import com.festimania.utils.IdGenerator;
import com.festimania.utils.enums.CountryEnum;
import com.festimania.utils.enums.GenreEnum;
import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<Artist> findAllArtists() {
        return List.of();
    }

    @Override
    public List<Artist> findFestivalsByName(String name) {
        return List.of();
    }

    @Override
    public List<Artist> findFestivalsByGenre(GenreEnum genre) {
        return List.of();
    }

    @Override
    public Artist findByName(String name) {
        return null;
    }

    @Override
    public Artist findById(String id) {
        return null;
    }

    @Override
    public Artist createArtist(ArtistDto newArtist) throws AttributeException {

        try {

            if ( artistRepository.existsArtistByName(newArtist.getName()) )
                throw new AttributeException("El Artista " + newArtist.getName() + " ya existe");
            if ( newArtist.getName().isBlank() )
                throw new AttributeException("El nombre del artista esta vacío ");

            Artist artist = Artist.builder()
                    ._id(IdGenerator.generateId())
                    .name(newArtist.getName())
                    .genre(GenreEnum.convertStringToGenreEnum(newArtist.getGenre()))
                    .bio(newArtist.getBio())
                    .country(CountryEnum.convertStringToCountryEnum(newArtist.getCountry()))
                    .socialMedia(newArtist.getSocialMedia())
                    .build();

            return mongoTemplate.save(artist, "artistas");

        } catch (Exception e) {
            e.printStackTrace();
            throw new AttributeException("Ha habido algún priblema al insertar el artista " + newArtist.getName());
        }

    }

    @Override
    public boolean alterArtist(ArtistDto changeArtist) {
        return false;
    }

    @Override
    public boolean deleteArtist(String idArtist) {
        return false;
    }

}
