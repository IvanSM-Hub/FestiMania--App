package com.festimania.persistence.service.impl;

import com.festimania.entities.Festival;
import com.festimania.entities.dto.FestivalCompleteDto;
import com.festimania.entities.dto.FestivalDto;
import com.festimania.exceptions.AttributeException;
import com.festimania.exceptions.ObjectNotFoundException;
import com.festimania.persistence.repositories.FestivalRepository;
import com.festimania.persistence.service.FestivalService;
import com.festimania.utils.ClassMapper;
import com.festimania.utils.IdGenerator;
import com.festimania.utils.enums.GenreEnum;
import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
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
                .map(classMapper::toFestivalCompleteDto)
                .collect(Collectors.toList());
    }

    @Override
    public FestivalCompleteDto findFestivalsById(String id) {
        if ( !festivalRepository.existsFestivalBy_id(id) )
            throw new ObjectNotFoundException("El Artista con el id: " + id + " no existe.");
        return classMapper.toFestivalCompleteDto( Objects.requireNonNull(mongoTemplate.findById(id, Festival.class)) );
    }

    @Override
    public List<FestivalCompleteDto> findFestivalsByName(String name) {
        String nameDepurated = name.replace('_',' ');
        List<Festival> festivalList = festivalRepository.findByNameContainingIgnoreCase(nameDepurated);
        if (festivalList.isEmpty())
            throw new ObjectNotFoundException("No se encontraron festivales con el nombre: " + nameDepurated);
        return festivalList.stream()
                .map(classMapper::toFestivalCompleteDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FestivalCompleteDto> findFestivalsByDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateSearch = LocalDate.parse(date, formatter);

        List<Festival> festivalList = festivalRepository.findFestivalsByDate(dateSearch);

        return festivalList.stream()
                .map(classMapper::toFestivalCompleteDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FestivalCompleteDto> findFestivalsByGenre(String genre) throws AttributeException {
        List<Festival> festivalList = festivalRepository.findFestivalsByGenre(GenreEnum.convertStringToGenreEnum(genre));
        System.out.println(festivalList);
        if ( festivalList.isEmpty() )
            throw new ObjectNotFoundException("No se encontrarón festivales con este genero: " + genre +".");
        return festivalList.stream()
                .map(classMapper::toFestivalCompleteDto)
                .collect(Collectors.toList());
    }

    @Override
    public FestivalCompleteDto createFestival(FestivalDto newFestival) throws AttributeException {
        try {

            if ( festivalRepository.existsFestivalByNameIgnoreCase(newFestival.getName()) )
                throw new Exception("El Artista " + newFestival.getName() + " ya existe");
            if ( newFestival.getName().isBlank() )
                throw new AttributeException("El nombre del festival esta vacío");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateStart = LocalDate.parse(newFestival.getDateStart(), formatter);
            LocalDate dateEnd = LocalDate.parse(newFestival.getDateEnd(), formatter);
            if ( dateStart.isAfter(dateEnd) )
                throw new AttributeException("La fecha de inicio: " + dateStart +
                        " no puede ser posterior a la de finalización: " + dateEnd + ".");

            Festival festival = Festival.builder()
                    ._id(IdGenerator.generateId())
                    .name(newFestival.getName())
                    .description(newFestival.getDescription())
                    .dateStart(dateStart)
                    .dateEnd(dateEnd)
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
    public FestivalCompleteDto alterFestival(String id, FestivalDto updateFestival) throws AttributeException {

        if ( festivalRepository.existsFestivalByNameIgnoreCase(id) )
           throw new ObjectNotFoundException("El festival con el id: "+ id + ", no existe.");

        if ( updateFestival.getName().isBlank() )
            throw new AttributeException("El nombre del festival esta vacío");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateStart = LocalDate.parse(
                updateFestival.getDateStart().replace('/','-'),
                formatter
        );
        LocalDate dateEnd = LocalDate.parse(
                updateFestival.getDateEnd().replace('/','-'),
                formatter
        );
        if ( dateStart.isAfter(dateEnd) )
            throw new AttributeException("La fecha de inicio: " + dateStart +
                    " no puede ser posterior a la de finalización: " + dateEnd + ".");

        Festival festival = Festival.builder()
                ._id(id)
                .name(updateFestival.getName())
                .description(updateFestival.getDescription())
                .dateStart(dateStart)
                .dateEnd(dateEnd)
                .location(updateFestival.getLocation())
                .genre(GenreEnum.convertStringToGenreEnum(updateFestival.getGenre()))
                .artistsId(updateFestival.getArtistsId())
                .build();

        festivalRepository.save(festival);

        return classMapper.toFestivalCompleteDto(festival);
    }

    @Override
    public boolean deleteFestival(String id) {
        Festival festival = mongoTemplate.findById(id, Festival.class);
        if ( festival == null )
            throw new ObjectNotFoundException("El Festival con el id: " + id + " no existe.");

        mongoTemplate.remove(festival, "festivales");
        return true;
    }

}
