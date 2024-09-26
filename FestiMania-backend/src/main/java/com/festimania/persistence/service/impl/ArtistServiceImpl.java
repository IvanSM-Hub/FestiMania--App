package com.festimania.persistence.service.impl;

import com.festimania.entities.Artist;
import com.festimania.entities.dto.ArtistDto;
import com.festimania.exceptions.AttributeException;
import com.festimania.exceptions.ObjectNotFoundException;
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
        return mongoTemplate.findAll(Artist.class);
    }

    @Override
    public List<Artist> findArtistsByName(String name) {
        List<Artist> artists = artistRepository.findByNameContainingIgnoreCase(name);
        System.out.println(artists);
        if (artists.isEmpty())
            throw new ObjectNotFoundException("No se encontraron artistas con el nombre: " + name);
        return artists;
    }

    @Override
    public List<Artist> findArtistsByGenre(String genre) throws AttributeException {
        return artistRepository.findArtistsByGenre(GenreEnum.convertStringToGenreEnum(genre));
    }

    @Override
    public Artist findById(String id) {

        if ( !artistRepository.existsArtistBy_id(id) )
            throw new ObjectNotFoundException("El Artista con el id: " + id + " no existe.");
        return mongoTemplate.findById(id, Artist.class);

    }

    @Override
    public Artist createArtist(ArtistDto newArtist) throws AttributeException {

        try {

            if ( artistRepository.existsArtistByName(newArtist.getName()) )
                throw new Exception("El Artista " + newArtist.getName() + " ya existe");
            if ( newArtist.getName().isBlank() )
                throw new AttributeException("El nombre del artista esta vacío");

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
    public Artist updateArtist(String id, ArtistDto updateArtist) throws AttributeException {

        if ( !artistRepository.existsArtistBy_id(id) )
            throw new ObjectNotFoundException("El Artista con el id: " + id + " no existe.");

        Artist artist = Artist.builder()
                ._id(id)
                .name(updateArtist.getName())
                .genre(GenreEnum.convertStringToGenreEnum(updateArtist.getGenre()))
                .bio(updateArtist.getBio())
                .country(CountryEnum.convertStringToCountryEnum(updateArtist.getCountry()))
                .socialMedia(updateArtist.getSocialMedia())
                .build();

        return mongoTemplate.save(artist);

    }

    @Override
    public boolean deleteArtist(String id) {
        Artist artist = mongoTemplate.findById(id, Artist.class);
        if ( artist == null )
            throw new ObjectNotFoundException("El Artista con el id: " + id + " no existe.");
        mongoTemplate.remove(artist,"artistas");
        return true;
    }

}
