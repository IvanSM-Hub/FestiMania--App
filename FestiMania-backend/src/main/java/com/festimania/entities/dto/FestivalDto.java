package com.festimania.entities.dto;

import com.festimania.utils.enums.GenreEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FestivalDto {

    private String name;
    private String description;
    private String genre;
    private String dateStart;
    private String dateEnd;
    private String location;
    private List<String> artistsId;

}
