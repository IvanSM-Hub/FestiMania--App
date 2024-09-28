package com.festimania.entities;

import com.festimania.utils.SocialMedia;
import com.festimania.utils.enums.CountryEnum;
import com.festimania.utils.enums.GenreEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
