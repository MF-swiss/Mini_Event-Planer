package ch.swiss.eventbackend.mapper;

import ch.swiss.eventbackend.dto.ArtistDTO;
import ch.swiss.eventbackend.model.Artist;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {

    public ArtistDTO toDTO(Artist artist) {
        return new ArtistDTO(
                artist.getId(),
                artist.getName(),
                artist.getGenre(),
                artist.getOrigin(),
                artist.getExperience(),
                artist.getDescription()
        );
    }

    public Artist toEntity(ArtistDTO dto) {
        Artist artist = new Artist();
        artist.setName(dto.name());
        artist.setGenre(dto.genre());
        artist.setOrigin(dto.origin());
        artist.setExperience(dto.experience());
        artist.setDescription(dto.description());
        return artist;
    }
}