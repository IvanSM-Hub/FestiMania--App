package com.festimania.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.festimania.utils.enums.GenreEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "festivales")
public class Festival {

    @Id
    private String _id;
    private String name;
    private String description;
    private GenreEnum genre;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate dateStart;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate dateEnd;
    private String location;
    private List<String> artistsId;

}
