package com.festimania.controllers;

import com.festimania.entities.Artist;
import com.festimania.entities.dto.ArtistDto;
import com.festimania.exceptions.AttributeException;
import com.festimania.exceptions.ObjectNotFoundException;
import com.festimania.persistence.service.ArtistService;
import jakarta.websocket.server.PathParam;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping()
    public ResponseEntity<Artist> highArtist( @RequestBody ArtistDto newArtist ) throws AttributeException {
        return ResponseEntity.ok(artistService.createArtist(newArtist));
    }

    @GetMapping("/find")
    public ResponseEntity<?> findArtist(
            @RequestParam(required = false) String _id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String genre
    ) throws ObjectNotFoundException, AttributeException {

        if (_id != null)
            return ResponseEntity.ok(artistService.findById(_id));
        else if (name != null)
            return ResponseEntity.ok(artistService.findArtistsByName(name));
        else if (genre != null)
            return ResponseEntity.ok(artistService.findArtistsByGenre(genre));
        else
            throw new ObjectNotFoundException(
                    "Debes proporcionar un _id, un nombre o un genero musical parabuscar el artista."
            );
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<Artist>> findAllArtists() throws ObjectNotFoundException {
        return ResponseEntity.ok(artistService.findAllArtists());
    }

    @PutMapping("/update")
    public ResponseEntity<Artist> updateArtist(@RequestParam String _id, @RequestBody ArtistDto updateArtist ) throws AttributeException {
        return ResponseEntity.ok(artistService.updateArtist(_id, updateArtist));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteArtistById( @RequestParam String _id ) {
        return ResponseEntity.ok(artistService.deleteArtist(_id));
    }

}
