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
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String genre;
    private String origin;
    private String experience;
    @Column(columnDefinition = "TEXT")
    private String description;

    // Inverse Side: Artist (@OneToMany(mappedBy = "artist"))
    @JsonIgnore // verhindert Endlos-Schleife beim Serialisieren (Artist -> Events -> Artist -> ...)
    @OneToMany(mappedBy = "artist")
    private List<Event> events = new ArrayList<>();

    public Artist() {
        // JPA benötigt einen leeren Konstruktor
    }

    public Artist(String name, String genre, String origin, String experience, String description) {
        this.name = name;
        this.genre = genre;
        this.origin = origin;
        this.experience = experience;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
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