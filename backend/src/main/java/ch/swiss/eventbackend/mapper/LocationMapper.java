package ch.swiss.eventbackend.mapper;

import ch.swiss.eventbackend.dto.LocationDTO;
import ch.swiss.eventbackend.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public LocationDTO toDTO(Location location) {
        return new LocationDTO(
                location.getId(),
                location.getName(),
                location.getCity(),
                location.getCountry(),
                location.getType(),
                location.getCapacity(),
                location.getDescription()
        );
    }

    public Location toEntity(LocationDTO dto) {
        Location location = new Location();
        location.setName(dto.name());
        location.setCity(dto.city());
        location.setCountry(dto.country());
        location.setType(dto.type());
        location.setCapacity(dto.capacity());
        location.setDescription(dto.description());
        return location;
    }
}
