package main.java.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "djs")
public class DJ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String genre;
    private String origin;
    private String experience;
    private String description;

    // --- Beziehung: Ein DJ hat viele Events ---
    @OneToMany(mappedBy = "dj")
    private List<Event> events;

    // --- Konstruktoren ---
    public DJ() {
    }

    public DJ(String name, String genre, String origin, String experience, String description) {
        this.name = name;
        this.genre = genre;
        this.origin = origin;
        this.experience = experience;
        this.description = description;
    }

    // --- Getter & Setter ---
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
