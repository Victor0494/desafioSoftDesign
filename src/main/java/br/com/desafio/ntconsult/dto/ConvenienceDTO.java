package br.com.desafio.ntconsult.dto;

import br.com.desafio.ntconsult.entities.Convenience;

public record ConvenienceDTO(Boolean wifi, Boolean airConditioning, Boolean tv, Boolean parking) {

    public ConvenienceDTO(Convenience convenience) {
        this(convenience.getWifi(), convenience.getAirConditioning(), convenience.getTv(), convenience.getParking());
    }
}
