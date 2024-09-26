package com.festimania.persistence.service.impl;

import com.festimania.entities.Festival;
import com.festimania.entities.dto.FestivalCompleteDto;
import com.festimania.entities.dto.FestivalDto;
import com.festimania.exceptions.AttributeException;
import com.festimania.persistence.repositories.FestivalRepository;
import com.festimania.persistence.service.FestivalService;
import com.festimania.utils.ClassMapper;
import com.festimania.utils.IdGenerator;
import com.festimania.utils.enums.GenreEnum;
import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class FestivalServiceImpl implements FestivalService {

    private final FestivalRepository festivalRepository;
    private final MongoTemplate mongoTemplate;
    private final ClassMapper classMapper;


    @Override
    public List<FestivalCompleteDto> findAllFestivals() {
        return mongoTemplate.findAll(Festival.class).stream()
                .map( festival -> classMapper.toFestivalCompleteDto(festival) )
                .collect(Collectors.toList());
    }

    @Override
    public List<FestivalCompleteDto> findFestivalsById(String id) {
        return List.of();
    }

    @Override
    public List<FestivalCompleteDto> findFestivalsByName(String name) {
        return List.of();
    }

    @Override
    public List<FestivalCompleteDto> findFestivalsByDate(LocalDate date) {
        return List.of();
    }

    @Override
    public List<FestivalCompleteDto> findFestivalsByGenre(String genre) {
        return List.of();
    }

    @Override
    public FestivalCompleteDto createFestival(FestivalDto newFestival) throws AttributeException {
        try {

            if ( festivalRepository.existsFestivalByName(newFestival.getName()) )
                throw new Exception("El Artista " + newFestival.getName() + " ya existe");
            if ( newFestival.getName().isBlank() )
                throw new AttributeException("El nombre del festival esta vacío");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

            Festival festival = Festival.builder()
                    ._id(IdGenerator.generateId())
                    .name(newFestival.getName())
                    .description(newFestival.getDescription())
                    .dateStart(LocalDate.parse(newFestival.getDateStart(), formatter))
                    .dateEnd(LocalDate.parse(newFestival.getDateEnd(), formatter))
                    .location(newFestival.getLocation())
                    .genre(GenreEnum.convertStringToGenreEnum(newFestival.getGenre()))
                    .artistsId(newFestival.getArtistsId())
                    .build();

            festivalRepository.save(festival);

            return classMapper.toFestivalCompleteDto(festival);

        }catch (Exception e) {
            throw new AttributeException("Ha habido algún priblema al insertar el artista " + newFestival.getName());
        }

    }

    @Override
    public FestivalCompleteDto alterFestival() {
        return null;
    }

    @Override
    public FestivalCompleteDto addArtistToFestival(List<String> artistsIdList, String idFestival) {
        return null;
    }
}
