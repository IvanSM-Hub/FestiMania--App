package com.festimania.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalDto {

    private String name;
    private String description;
    private String genre;
    private String dateStart;
    private String dateEnd;
    private String location;
    private List<String> artistsId;

}
