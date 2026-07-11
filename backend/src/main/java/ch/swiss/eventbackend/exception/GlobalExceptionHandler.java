package ch.swiss.eventbackend.exception;

import ch.swiss.eventbackend.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Zentrale Fehlerbehandlung für die gesamte REST-API.
 *
 * Statt dass jeder Controller Fehler einzeln (oder gar nicht) behandelt,
 * fängt diese Klasse alle relevanten Exceptions ab und wandelt sie in
 * eine einheitliche {@link ErrorResponse} um. Dadurch stürzt die App bei
 * fehlerhaften Eingaben oder nicht gefundenen Ressourcen nie mit einem
 * rohen Stacktrace ab, sondern liefert immer eine wohlgeformte Antwort.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Wird geworfen, wenn Event/Location/Artist per ID nicht gefunden wurde -> 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Wird automatisch von Spring geworfen, wenn ein mit @Valid annotiertes
    // Form-DTO gegen seine Bean-Validation-Regeln verstösst (z.B. leerer
    // Titel) -> 400 mit einer Übersicht aller fehlerhaften Felder.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validierung fehlgeschlagen",
                fieldErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Auffangnetz für alles Unerwartete, damit niemals ein roher
    // Stacktrace oder ein leerer 500er ohne Erklärung beim Client landet.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ein unerwarteter Fehler ist aufgetreten"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}