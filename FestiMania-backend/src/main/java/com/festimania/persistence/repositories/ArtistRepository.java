package com.festimania.persistence.repositories;

import com.festimania.entities.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends MongoRepository<Artist, String> {

    boolean existsArtistByName( String name );

}
