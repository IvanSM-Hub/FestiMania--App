package com.festimania.persistence.repositories;

import com.festimania.entities.Artist;
import com.festimania.utils.enums.GenreEnum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends MongoRepository<Artist, String> {

    boolean existsArtistByName( String name );
    boolean existsArtistBy_id( String id );

    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    List<Artist> findByNameContainingIgnoreCase(String name);

    List<Artist> findArtistsByGenre(GenreEnum genreEnum);

}
