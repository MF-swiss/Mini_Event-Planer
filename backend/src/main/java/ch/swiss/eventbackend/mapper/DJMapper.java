package ch.swiss.eventbackend.mapper;

import java.util.Map;

import ch.swiss.eventbackend.model.DJ;

public class DJMapper {

    public static DJ fromJson(Map<String, Object> json) {
        DJ dj = new DJ();
        dj.setName((String) json.get("name"));
        dj.setGenre((String) json.get("genre"));
        dj.setOrigin((String) json.get("origin"));
        dj.setExperience((String) json.get("experience"));
        dj.setDescription((String) json.get("description"));
        return dj;
    }
}
