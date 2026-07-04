package ch.swiss.eventbackend.dto;

import java.time.LocalDate;

public record EventDTO(
        Long id,
        String title,
        LocalDate date,
        String description,
        Long locationId,
        Long artistId
) {}
