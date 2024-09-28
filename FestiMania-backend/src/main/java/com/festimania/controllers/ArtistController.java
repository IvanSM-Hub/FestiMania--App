package com.festimania.controllers;

import com.festimania.entities.Artist;
import com.festimania.entities.dto.ArtistDto;
import com.festimania.exceptions.AttributeException;
import com.festimania.exceptions.ObjectNotFoundException;
import com.festimania.persistence.service.ArtistService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    private final ArtistService artistService;

    /**
     * Crea un nuevo artista en la base de datos.
     * @param newArtist Objeto de tipo ArtistDto que contiene los datos del nuevo artista.
     * @return ResponseEntity con el objeto Artist creado.
     * @throws AttributeException Si alguno de los atributos del nuevo artista no es valido.
     */
    @PostMapping({"","/"})
    public ResponseEntity<Artist> highArtist( @RequestBody ArtistDto newArtist ) throws AttributeException {
        return ResponseEntity.ok(artistService.createArtist(newArtist));
    }

    /**
     * Busca un artista en la base de datos.
     * @param _id Identificador del artista a buscar.
     * @param name Nombre del artista a buscar.
     * @param genre Genero musical del artista a buscar.
     * @return ResponseEntity con el objeto Artist encontrado.
     * @throws ObjectNotFoundException Si no se encuentra ningun artista con los parametros proporcionados.
     * @throws AttributeException Si no se proporciona ningun parametro para buscar el artista.
     */
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

    /**
     * Busca todos los artistas en la base de datos.
     * @return ResponseEntity con una lista de objetos Artist.
     * @throws ObjectNotFoundException Si no se encuentran artistas en la base de datos.
     */
    @GetMapping({"", "/"})
    public ResponseEntity<List<Artist>> findAllArtists() throws ObjectNotFoundException {
        return ResponseEntity.ok(artistService.findAllArtists());
    }

    /**
     * Actualiza los datos de un artista en la base de datos.
     * @param _id Identificador del artista a actualizar.
     * @param updateArtist Objeto de tipo ArtistDto que contiene los datos actualizados del artista.
     * @return ResponseEntity con el objeto Artist actualizado.
     * @throws AttributeException Si alguno de los atributos del artista a actualizar no es valido.
     */
    @PutMapping("/update")
    public ResponseEntity<Artist> updateArtist(@RequestParam String _id, @RequestBody ArtistDto updateArtist ) throws AttributeException {
        return ResponseEntity.ok(artistService.updateArtist(_id, updateArtist));
    }

    /**
     * Elimina un artista de la base de datos.
     * @param _id Identificador del artista a eliminar.
     * @return ResponseEntity con un booleano que indica si el artista fue eliminado.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteArtistById( @RequestParam String _id ) {
        return ResponseEntity.ok(artistService.deleteArtist(_id));
    }

}
