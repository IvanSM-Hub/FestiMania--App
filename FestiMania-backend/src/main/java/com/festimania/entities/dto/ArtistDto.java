package com.festimania.entities.dto;

import com.festimania.utils.SocialMedia;
import com.festimania.utils.enums.CountryEnum;
import com.festimania.utils.enums.GenreEnum;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ArtistDto {

    private String name;
    private String genre;
    private String bio;
    private String country;
    private SocialMedia socialMedia;

}
