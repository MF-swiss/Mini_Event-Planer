package ch.swiss.eventbackend.mapper;

import java.util.Map;

import ch.swiss.eventbackend.model.Location;

public class LocationMapper {

    public static Location fromJson(Map<String, Object> json) {
        Location location = new Location();
        location.setName((String) json.get("name"));
        location.setCity((String) json.get("city"));
        location.setCountry((String) json.get("country"));
        location.setType((String) json.get("type"));
        location.setCapacity((Integer) json.get("capacity"));
        location.setDescription((String) json.get("description"));
        return location;
    }
}
