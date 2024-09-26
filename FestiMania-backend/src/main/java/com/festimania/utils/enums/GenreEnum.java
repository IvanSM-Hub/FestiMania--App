package com.festimania.utils.enums;

import com.festimania.exceptions.AttributeException;

public enum GenreEnum {
    ROCK, POP, HIP_HOP, RAP, ELECTRONICA, REGGAE, JAZZ, BLUES, FOLK, COUNTRY, R_B, SOUL, LATINO, CLASICA, METAL, INDIE, PUNK;

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

