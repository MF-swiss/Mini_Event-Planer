package ch.swiss.eventbackend.dto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Einheitliches Format für alle Fehlerantworten der API.
 * fieldErrors ist nur bei Validierungsfehlern (400) befüllt und enthält
 * pro fehlerhaftem Feld die zugehörige Fehlermeldung, sonst null.
 */
public record ErrorResponse(
        int status,
        String message,
        LocalDateTime timestamp,
        Map<String, String> fieldErrors
) {

    // Kurzform für einfache Fehler ohne Feld-Details (z.B. 404, 500)
    public ErrorResponse(int status, String message) {
        this(status, message, LocalDateTime.now(), null);
    }

    // Kurzform für Validierungsfehler (400) mit Feld-Details
    public ErrorResponse(int status, String message, Map<String, String> fieldErrors) {
        this(status, message, LocalDateTime.now(), fieldErrors);
    }
}