package com.festimania.controllers;

import com.festimania.entities.dto.FestivalCompleteDto;
import com.festimania.entities.dto.FestivalDto;
import com.festimania.exceptions.AttributeException;
import com.festimania.exceptions.ObjectNotFoundException;
import com.festimania.persistence.service.FestivalService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/festival")
public class FestivalController {

    private final FestivalService festivalService;

    /**
     * Crea un nuevo festival en la base de datos.
     * @param newFestival Objeto de tipo FestivalDto que contiene los datos del nuevo festival.
     * @return ResponseEntity con el objeto Festival creado.
     * @throws AttributeException Si alguno de los atributos del nuevo festival no es valido.
     */
    @PostMapping({"","/"})
    public ResponseEntity<FestivalCompleteDto> highFestival(
            @RequestBody FestivalDto newFestival
    )throws AttributeException {
        return ResponseEntity.ok( festivalService.createFestival( newFestival ) );
    }

    /**
     * Busca un festival en la base de datos.
     * @param _id Identificador del festival a buscar.
     * @param name Nombre del festival a buscar.
     * @param genre Genero musical del festival a buscar.
     * @param date Fecha del festival a buscar.
     * @return ResponseEntity con el objeto Festival encontrado.
     * @throws ObjectNotFoundException Si no se encuentra ningun festival con los parametros proporcionados.
     * @throws AttributeException Si no se proporciona ningun parametro para buscar el festival.
     */
    @GetMapping("/find")
    public ResponseEntity<?> findArtist(
            @RequestParam(required = false) String _id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String date
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
                    "Debes proporcionar un _id, un nombre, un genero músical o una fecha para buscar el festival."
            );
    }

    /**
     * Busca todos los festivales en la base de datos.
     * @return ResponseEntity con una lista de objetos Festival.
     * @throws ObjectNotFoundException Si no se encuentra ningun festival en la base de datos.
     */
    @GetMapping({"", "/"})
    public ResponseEntity<List<FestivalCompleteDto>> findAllArtists() throws ObjectNotFoundException {
        return ResponseEntity.ok(festivalService.findAllFestivals());
    }

    /**
     * Actualiza los datos de un festival en la base de datos.
     * @param _id Identificador del festival a actualizar.
     * @param updateFestival Objeto de tipo FestivalDto que contiene los datos actualizados del festival.
     * @return ResponseEntity con el objeto Festival actualizado.
     * @throws AttributeException Si alguno de los atributos del festival a actualizar no es valido.
     */
    @PutMapping("/update")
    public ResponseEntity<FestivalCompleteDto> updateFestival(
            @RequestParam String _id,
            @RequestBody FestivalDto updateFestival
    ) throws AttributeException {
        return ResponseEntity.ok( festivalService.alterFestival(_id, updateFestival) );
    }

    /**
     * Elimina un festival de la base de datos.
     * @param _id Identificador del festival a eliminar.
     * @return ResponseEntity con un booleano que indica si el festival ha sido eliminado.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteFestival(@RequestParam String _id) {
        return ResponseEntity.ok(festivalService.deleteFestival(_id));
    }

}
