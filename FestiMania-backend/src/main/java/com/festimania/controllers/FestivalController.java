package com.festimania.controllers;

import com.festimania.entities.Artist;
import com.festimania.entities.dto.FestivalCompleteDto;
import com.festimania.entities.dto.FestivalDto;
import com.festimania.exceptions.AttributeException;
import com.festimania.exceptions.ObjectNotFoundException;
import com.festimania.persistence.service.FestivalService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Data
@RestController
@RequestMapping("/api/festival")
public class FestivalController {

    private final FestivalService festivalService;

    @PostMapping({"","/"})
    public ResponseEntity<FestivalCompleteDto> highFestival(
            @RequestBody FestivalDto newFestival
    )throws AttributeException {
        return ResponseEntity.ok( festivalService.createFestival( newFestival ) );
    }

    @GetMapping("/find")
    public ResponseEntity<?> findArtist(
            @RequestParam(required = false) String _id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false)LocalDate date
            ) throws ObjectNotFoundException, AttributeException {

        if (_id != null)
            return ResponseEntity.ok(festivalService.findFestivalsById(_id));
        else if (name != null)
            return ResponseEntity.ok(festivalService.findFestivalsByName(name));
        else if (genre != null)
            return ResponseEntity.ok(festivalService.findFestivalsByGenre(genre));
        else if (date != null)
            return ResponseEntity.ok(festivalService.findFestivalsByDate(date));
        else
            throw new ObjectNotFoundException(
                    "Debes proporcionar un _id, un nombre o un genero musical parabuscar el artista."
            );
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<FestivalCompleteDto>> findAllArtists() throws ObjectNotFoundException {
        return ResponseEntity.ok(festivalService.findAllFestivals());
    }

}
