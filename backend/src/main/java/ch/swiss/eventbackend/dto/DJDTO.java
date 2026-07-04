package ch.swiss.eventbackend.dto;

public record DJDTO(
        Long id,
        String name,
        String genre,
        String origin,
        String experience,
        String description
) {}
