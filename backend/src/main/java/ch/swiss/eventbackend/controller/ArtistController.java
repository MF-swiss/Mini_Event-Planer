package ch.swiss.eventbackend.controller;

import ch.swiss.eventbackend.dto.ArtistDTO;
import ch.swiss.eventbackend.exception.ResourceNotFoundException;
import ch.swiss.eventbackend.mapper.ArtistMapper;
import ch.swiss.eventbackend.model.Artist;
import ch.swiss.eventbackend.service.ArtistService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;
    private final ArtistMapper artistMapper;

    public ArtistController(ArtistService artistService, ArtistMapper artistMapper) {
        this.artistService = artistService;
        this.artistMapper = artistMapper;
    }

    @GetMapping
    public List<ArtistDTO> getAllArtists() {
        return artistService.getAllArtists()
                .stream()
                .map(artistMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ArtistDTO getArtistById(@PathVariable Long id) {
        Artist artist = artistService.getArtistById(id);
        if (artist == null) {
            throw new ResourceNotFoundException("Artist mit ID " + id + " wurde nicht gefunden");
        }
        return artistMapper.toDTO(artist);
    }

    // @Valid sorgt dafür, dass z.B. ein leerer Name aus dem
    // ArtistModal mit 400 + Feld-Fehlern abgelehnt wird.
    @PostMapping
    public ArtistDTO createArtist(@Valid @RequestBody ArtistDTO dto) {
        Artist artist = artistMapper.toEntity(dto);
        Artist saved = artistService.saveArtist(artist);
        return artistMapper.toDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
    }
}