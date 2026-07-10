package ch.swiss.eventbackend.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private String country;
    private String type;

    private int capacity;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Inverse Side: Location (@OneToMany(mappedBy = "location"))
    @JsonIgnore // verhindert Endlos-Schleife beim Serialisieren (Location -> Events -> Location -> ...)
    @OneToMany(mappedBy = "location")
    private List<Event> events = new ArrayList<>();

    public Location() {
        // JPA benötigt einen leeren Konstruktor
    }

    public Location(String name, String city, String country, String type, int capacity, String description) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.type = type;
        this.capacity = capacity;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}