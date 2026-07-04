package ch.swiss.eventbackend.dto;

public record ArtistDTO(
        Long id,
        String name,
        String genre,
        String origin,
        String experience,
        String description
) {}