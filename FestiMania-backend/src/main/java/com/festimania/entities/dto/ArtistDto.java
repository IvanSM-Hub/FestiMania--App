package com.festimania.entities.dto;

import com.festimania.utils.SocialMedia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDto {

    private String name;
    private String genre;
    private String bio;
    private String country;
    private SocialMedia socialMedia;

}
