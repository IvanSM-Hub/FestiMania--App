package com.festimania.utils.enums;

import com.festimania.exceptions.AttributeException;

public enum CountryEnum {
    ARGENTINA,
    BRASIL,
    CHILE,
    COLOMBIA,
    ESPAÑA,
    ESTADOS_UNIDOS,
    FRANCIA,
    GERMANIA,
    MEXICO,
    REINO_UNIDO,
    RUSIA,
    SUECIA,
    CANADA,
    AUSTRALIA,
    JAPÓN,
    INDIA,
    ITALIA,
    SUDÁFRICA;

    public static CountryEnum convertStringToCountryEnum(String countryString) throws AttributeException {
        if (countryString == null || countryString.isEmpty()) {
            throw new AttributeException("Género no válido: " + countryString);
        }
        try {
            return CountryEnum.valueOf(countryString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AttributeException("Páis no válido: " + countryString);
        }
    }
}