package com.festimania.entities;

import com.festimania.utils.SocialMedia;
import com.festimania.utils.enums.CountryEnum;
import com.festimania.utils.enums.GenreEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

@Builder
@Data
@Document(collection = "artistas")
public class Artist {

    @Id
    private String _id;
    private String name;
    private GenreEnum genre;
    private String bio;
    private CountryEnum country;
    private SocialMedia socialMedia;

}
