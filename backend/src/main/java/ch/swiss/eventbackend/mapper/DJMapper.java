package ch.swiss.eventbackend.mapper;

import ch.swiss.eventbackend.dto.DJDTO;
import ch.swiss.eventbackend.model.DJ;
import org.springframework.stereotype.Component;

@Component
public class DJMapper {

    public DJDTO toDTO(DJ dj) {
        return new DJDTO(
                dj.getId(),
                dj.getName(),
                dj.getGenre(),
                dj.getOrigin(),
                dj.getExperience(),
                dj.getDescription()
        );
    }

    public DJ toEntity(DJDTO dto) {
        DJ dj = new DJ();
        dj.setName(dto.name());
        dj.setGenre(dto.genre());
        dj.setOrigin(dto.origin());
        dj.setExperience(dto.experience());
        dj.setDescription(dto.description());
        return dj;
    }
}
