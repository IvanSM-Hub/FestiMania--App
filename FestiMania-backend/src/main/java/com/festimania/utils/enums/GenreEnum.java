package com.festimania.utils.enums;

import com.festimania.exceptions.AttributeException;

public enum GenreEnum {
    ROCK, POP, HIP_HOP, RAP, ELECTRONICA, REGGAE, JAZZ, BLUES, FOLK, COUNTRY, R_B, SOUL, LATINO, CLASICA, METAL, INDIE, PUNK;

    /**
     * Convierte un String a un GenreEnum.
     * @param genreString String a convertir.
     * @return GenreEnum.
     * @throws AttributeException Si el String no es un género válido.
     */
    public static GenreEnum convertStringToGenreEnum(String genreString) throws AttributeException {
        if (genreString == null || genreString.isEmpty())
            throw new AttributeException("Género no válido: " + genreString);
        try {
            return GenreEnum.valueOf(genreString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AttributeException("Género no válido: " + genreString);
        }
    }
}

