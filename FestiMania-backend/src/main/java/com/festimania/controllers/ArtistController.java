package com.festimania.controllers;

import com.festimania.entities.Artist;
import com.festimania.entities.dto.ArtistDto;
import com.festimania.exceptions.AttributeException;
import com.festimania.persistence.service.ArtistService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping()
    public ResponseEntity<Artist> highArtist( @RequestBody ArtistDto newArtist ) throws AttributeException {
        return ResponseEntity.ok(artistService.createArtist(newArtist));
    }


}
