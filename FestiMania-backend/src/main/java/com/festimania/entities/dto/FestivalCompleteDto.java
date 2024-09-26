package com.festimania.entities.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.festimania.utils.enums.GenreEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class FestivalCompleteDto {

    private String name;
    private String description;
    private String genre;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate dateStart;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate dateEnd;

    private String location;

    private List<ArtistDto> artistsList;

}
