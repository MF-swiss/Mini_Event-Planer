package ch.swiss.eventbackend.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Dient sowohl als Response-DTO (GET) als auch als Form-DTO (POST via
 * ArtistModal im Frontend). "id" wird beim Erstellen ignoriert.
 */
public record ArtistDTO(
        Long id,

        @NotBlank(message = "Name ist erforderlich")
        String name,

        String genre,
        String origin,
        String experience,
        String description
) {}