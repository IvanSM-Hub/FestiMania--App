package com.festimania.entities;

import com.festimania.utils.enums.GenreEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Builder
@Data
@Document(collection = "festivales")
public class Festival {

    @Id
    private String _id;
    private String name;
    private String description;
    private GenreEnum genre;
    private Date dateStart;
    private Date dateEnd;
    private String location;
    private List<String> artistsId;

}
