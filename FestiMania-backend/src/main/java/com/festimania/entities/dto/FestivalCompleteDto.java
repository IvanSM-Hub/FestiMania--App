package com.festimania.entities.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
