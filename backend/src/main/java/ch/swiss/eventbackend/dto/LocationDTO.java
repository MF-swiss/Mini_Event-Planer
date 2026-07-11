package ch.swiss.eventbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Dient sowohl als Response-DTO (GET) als auch als Form-DTO (POST via
 * LocationModal im Frontend). "id" wird beim Erstellen ignoriert.
 */
public record LocationDTO(
        Long id,

        @NotBlank(message = "Name ist erforderlich")
        String name,

        @NotBlank(message = "Stadt ist erforderlich")
        String city,

        String country,

        String type,

        @PositiveOrZero(message = "Kapazität darf nicht negativ sein")
        int capacity,

        String description
) {}