package com.festimania.utils;

import com.festimania.entities.Artist;
import com.festimania.entities.Festival;
import com.festimania.entities.dto.ArtistDto;
import com.festimania.entities.dto.FestivalCompleteDto;
import com.festimania.persistence.service.ArtistService;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Component
public class ClassMapper {

    private final ArtistService artistService;

        /**
         * Mapea un objeto de tipo Festival a un objeto de tipo FestivalCompleteDto.
         * @param festival Objeto de tipo Festival a mapear.
         * @return Objeto de tipo FestivalCompleteDto mapeado.
         */
    public FestivalCompleteDto toFestivalCompleteDto(Festival festival) {
        List<Artist> artistsList = festival.getArtistsId().stream()
                .map(artistService::findById)
                .collect(Collectors.toList());

        List<ArtistDto> artistsListDto = artistsList.stream()
                .map( artist -> toArtistDto(artist) )
                .collect(Collectors.toList());

        return FestivalCompleteDto.builder()
                .name(festival.getName())
                .description(festival.getDescription())
                .genre(festival.getGenre().name()) // Mapea el enum a su valor de texto
                .dateStart(festival.getDateStart())
                .dateEnd(festival.getDateEnd())
                .location(festival.getLocation())
                .artistsList(artistsListDto)
                .build();
    }

        /**
         * Mapea un objeto de tipo Artist a un objeto de tipo ArtistDto.
         * @param artist Objeto de tipo Artist a mapear.
         * @return Objeto de tipo ArtistDto mapeado.
         */
    public ArtistDto toArtistDto(Artist artist) {
        return ArtistDto.builder()
                .name(artist.getName())
                .genre(artist.getGenre().name())  // Convertimos el enum a su representación en cadena
                .bio(artist.getBio())
                .country(artist.getCountry().name())  // Convertimos el enum a su representación en cadena
                .socialMedia(artist.getSocialMedia())  // Pasamos el objeto SocialMedia directamente
                .build();
    }

}
