package ch.swiss.eventbackend.dto;

public record LocationDTO(
        Long id,
        String name,
        String city,
        String country,
        String type,
        int capacity,
        String description
) {}
