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

    /**
     * Busca todos los artistas en la base de datos.
     * @return Lista con todos los artistas encontrados.
     */
    @Override
    public List<Artist> findAllArtists() {
        return mongoTemplate.findAll(Artist.class);
    }

    /**
     * Busca un artista en la base de datos.
     * @param name Nombre del artista a buscar.
     * @return Lista con los artistas encontrados.
     * @throws ObjectNotFoundException Si no se encuentra ningun artista con el nombre proporcionado.
     */
    @Override
    public List<Artist> findArtistsByName(String name) {
        List<Artist> artists = artistRepository.findByNameContainingIgnoreCase(name);
        if (artists.isEmpty())
            throw new ObjectNotFoundException("No se encontraron artistas con el nombre: " + name);
        return artists;
    }

    /**
     * Busca un artista en la base de datos.
     * @param genre Genero musical del artista a buscar.
     * @return Lista con los artistas encontrados.
     * @throws ObjectNotFoundException Si no se encuentra ningun artista con el genero proporcionado.
     */
    @Override
    public List<Artist> findArtistsByGenre(String genre) throws AttributeException {
        return artistRepository.findArtistsByGenre(GenreEnum.convertStringToGenreEnum(genre));
    }

    /**
     * Busca un artista en la base de datos.
     * @param id Identificador del artista a buscar.
     * @return Objeto Artist encontrado.
     * @throws ObjectNotFoundException Si no se encuentra ningun artista con el id proporcionado.
     */
    @Override
    public Artist findById(String id) {

        if ( !artistRepository.existsArtistBy_id(id) )
            throw new ObjectNotFoundException("El Artista con el id: " + id + " no existe.");
        return mongoTemplate.findById(id, Artist.class);

    }

    /**
     * Crea un nuevo artista en la base de datos.
     * @param newArtist Objeto de tipo ArtistDto que contiene los datos del nuevo artista.
     * @return Objeto Artist creado.
     * @throws AttributeException Si alguno de los atributos del nuevo artista no es valido.
     */
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

    /**
     * Actualiza los datos de un artista en la base de datos.
     * @param id Identificador del artista a actualizar.
     * @param updateArtist Objeto de tipo ArtistDto que contiene los datos actualizados del artista.
     * @return Objeto Artist actualizado.
     * @throws AttributeException Si alguno de los atributos del artista a actualizar no es valido.
     */
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

    /**
     * Elimina un artista de la base de datos.
     * @param id Identificador del artista a eliminar.
     * @return True si se ha eliminado el artista.
     * @throws ObjectNotFoundException Si no se encuentra ningun artista con el id proporcionado.
     */
    @Override
    public boolean deleteArtist(String id) {
        Artist artist = mongoTemplate.findById(id, Artist.class);
        if ( artist == null )
            throw new ObjectNotFoundException("El Artista con el id: " + id + " no existe.");
        mongoTemplate.remove(artist,"artistas");
        return true;
    }

}
