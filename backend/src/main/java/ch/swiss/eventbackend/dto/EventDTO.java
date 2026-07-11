package ch.swiss.eventbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Dient sowohl als Response-DTO (GET) als auch als Form-DTO (POST/PUT).
 * Die Validation-Annotationen greifen nur, wenn der Controller den
 * Parameter mit @Valid entgegennimmt (also bei POST/PUT) - bei der
 * Serialisierung als Response-Body werden sie nicht ausgewertet.
 *
 * "id" ist absichtlich nicht validiert: beim Erstellen wird sie vom
 * Client nicht mitgeschickt (bzw. ignoriert), beim Lesen ist sie immer
 * gesetzt.
 */
public record EventDTO(
        Long id,

        @NotBlank(message = "Titel ist erforderlich")
        String title,

        @NotNull(message = "Datum ist erforderlich")
        LocalDate date,

        String description,

        @NotNull(message = "Location ist erforderlich")
        Long locationId,

        @NotNull(message = "Artist ist erforderlich")
        Long artistId
) {}