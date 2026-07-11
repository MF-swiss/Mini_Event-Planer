package ch.swiss.eventbackend.controller;

import ch.swiss.eventbackend.dto.LocationDTO;
import ch.swiss.eventbackend.exception.ResourceNotFoundException;
import ch.swiss.eventbackend.mapper.LocationMapper;
import ch.swiss.eventbackend.model.Location;
import ch.swiss.eventbackend.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;
    private final LocationMapper locationMapper;

    public LocationController(LocationService locationService, LocationMapper locationMapper) {
        this.locationService = locationService;
        this.locationMapper = locationMapper;
    }

    @GetMapping
    public List<LocationDTO> getAllLocations() {
        return locationService.getAllLocations()
                .stream()
                .map(locationMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public LocationDTO getLocationById(@PathVariable Long id) {
        Location location = locationService.getLocationById(id);
        if (location == null) {
            throw new ResourceNotFoundException("Location mit ID " + id + " wurde nicht gefunden");
        }
        return locationMapper.toDTO(location);
    }

    // @Valid sorgt dafür, dass z.B. eine leere Location aus dem
    // LocationModal (Name/Stadt fehlen) mit 400 + Feld-Fehlern
    // abgelehnt wird, statt einen kaputten Datensatz zu speichern.
    @PostMapping
    public LocationDTO createLocation(@Valid @RequestBody LocationDTO dto) {
        Location location = locationMapper.toEntity(dto);
        Location saved = locationService.saveLocation(location);
        return locationMapper.toDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
    }
}