package ch.swiss.eventbackend.exception;

/**
 * Wird geworfen, wenn eine angeforderte Ressource (Event, Location oder
 * Artist) anhand ihrer ID nicht in der Datenbank gefunden wird.
 * Wird zentral vom {@link GlobalExceptionHandler} in eine 404-Antwort
 * mit ErrorResponse-Body umgewandelt.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}