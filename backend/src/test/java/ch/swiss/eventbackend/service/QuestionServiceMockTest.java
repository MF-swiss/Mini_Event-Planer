package ch.swiss.eventbackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.swiss.eventbackend.dto.QuestionFormDTO;
import ch.swiss.eventbackend.exception.QuestionNotFoundException;
import ch.swiss.eventbackend.model.Question;
import ch.swiss.eventbackend.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceMockTest {
    @Mock
    private QuestionRepository repository; // Mock-Repo statt echtem Repository

    @InjectMocks
    private QuestionService service; // bekommt den Mock in den Konstruktor injiziert

    @Test
    void getQuestionByIdThrowsWhenNotFound() {
        // Arrange: dem Mock sagen, dass er für diese ID nichts findet
        given(repository.findById("unbekannt")).willReturn(Optional.empty());

        // Act + Assert: der Service muss die passende Exception werfen
        assertThatThrownBy(() -> service.getQuestionById("unbekannt"))
                .isInstanceOf(QuestionNotFoundException.class);
    }

    @Test
    void createQuestionGeneratesIdAndSaves() {
        // Arrange
        QuestionFormDTO dto = new QuestionFormDTO(
                "Wie heisst die Hauptstadt der Schweiz?",
                "Geographie",
                "leicht",
                List.of("Bern", "Zürich", "Genf", "Basel"),
                "Bern"
        );
        // Mock: save() gibt einfach das übergebene Objekt zurück
        given(repository.save(any(Question.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // Act
        Question created = service.createQuestion(dto);

        // Assert 1: der Service hat eine ID erzeugt
        assertThat(created.getId()).isNotBlank();

        // Assert 2: genau dieses Objekt wurde gespeichert
        ArgumentCaptor<Question> captor = ArgumentCaptor.forClass(Question.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getCategory()).isEqualTo("Geographie");
    }

    @Test
    void deleteQuestionCallsRepository() {
        // Falls dein deleteQuestion vorher die Existenz prüft (existsById),
        // musst du mit given(...).willReturn(true) prüfen, sonst wirft der Service vorzeitig:
        given(repository.existsById("5")).willReturn(true);
        service.deleteQuestion("5");

        verify(repository).deleteById("5");
    }
}