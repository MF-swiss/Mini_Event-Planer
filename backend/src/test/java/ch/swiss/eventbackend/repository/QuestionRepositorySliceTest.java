package ch.swiss.eventbackend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import ch.swiss.eventbackend.model.Question;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // gegen echte PostgreSQL, nicht H2
public class QuestionRepositorySliceTest {

    @Autowired
    private QuestionRepository repository;

    @Test
    void findByCategoryReturnsMatchingQuestions() {
        // Arrange: eine Test-Frage in der Kategorie Sport speichern
        Question sport = new Question();
        sport.setId("test-sport-1");
        sport.setText("Wie viele Spieler hat ein Fussballteam auf dem Feld?");
        sport.setCategory("Sport");
        sport.setDifficulty("leicht");
        sport.setAnswers(List.of("11", "9", "7", "5"));
        sport.setCorrectAnswer("11");
        repository.save(sport);

        // Act
        List<Question> result = repository.findByCategory("Sport");

        // Assert: es gibt Treffer, und ALLE sind aus der Kategorie Sport
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(q -> q.getCategory().equals("Sport"));
    }

    @Test
    void findByIdReturnsEmptyForUnknownId() {
        // Act
        Optional<Question> result = repository.findById("gibt-es-garantiert-nicht");

        // Assert
        assertThat(result).isEmpty();
    }

    /**
     * Zusatzaufgabe 2: Zweite Query testen
     */
    @Test
    void findByDifficultyReturnsMatchingQuestions() {
        Question schwer = new Question();
        schwer.setId("test-schwer-1");
        schwer.setText("In welchem Jahr wurde die Schweizer Bundesverfassung angenommen?");
        schwer.setCategory("Geschichte");
        schwer.setDifficulty("schwer");
        schwer.setAnswers(List.of("1848", "1291", "1815", "1914"));
        schwer.setCorrectAnswer("1848");
        repository.save(schwer);

        List<Question> result = repository.findByDifficulty("schwer");

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(q -> q.getDifficulty().equals("schwer"));
    }

}