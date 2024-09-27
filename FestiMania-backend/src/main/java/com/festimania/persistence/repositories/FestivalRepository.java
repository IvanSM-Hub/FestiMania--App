package com.festimania.persistence.repositories;

import com.festimania.entities.Festival;
import com.festimania.utils.enums.GenreEnum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FestivalRepository extends MongoRepository<Festival, String> {

    boolean existsFestivalByNameIgnoreCase( String name );
    boolean existsFestivalBy_id( String id );

    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    List<Festival> findByNameContainingIgnoreCase(String name);

    List<Festival> findFestivalsByGenre(GenreEnum genreEnum);

    @Query("{ 'dateStart' : { $lte: ?0 }, 'dateEnd' : { $gte: ?0 } }")
    List<Festival> findFestivalsByDate(LocalDate date);

}
