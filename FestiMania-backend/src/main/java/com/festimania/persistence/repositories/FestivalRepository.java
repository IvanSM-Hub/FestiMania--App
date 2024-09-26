package com.festimania.persistence.repositories;

import com.festimania.entities.Festival;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FestivalRepository extends MongoRepository<Festival, String> {

    boolean existsFestivalByName( String name );
    boolean existsFestivalBy_id( String id );

}
