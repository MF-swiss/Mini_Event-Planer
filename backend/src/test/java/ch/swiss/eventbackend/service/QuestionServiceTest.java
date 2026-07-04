package ch.swiss.eventbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ch.swiss.eventbackend.dto.QuestionFormDTO;
import ch.swiss.eventbackend.model.Question;
import ch.swiss.eventbackend.repository.QuestionRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUpTestData() {
        questionRepository.deleteAll();

        Question seeded = new Question();
        seeded.setId("1");
        seeded.setText("Wie viele Spieler stehen pro Mannschaft beim Fussball gleichzeitig auf dem Platz?");
        seeded.setCategory("Sport");
        seeded.setDifficulty("leicht");
        seeded.setAnswers(List.of("9", "10", "11", "12"));
        seeded.setCorrectAnswer("11");

        questionRepository.save(seeded);
    }

    @Test
    void getAllQuestionsReturnsData() {
        List<Question> question = questionService.getAllQuestions();
        assertFalse(question.isEmpty());
    }

    @Test
    void getQuestionByIdReturnsCorrectQuestion() {
        Question question = questionService.getQuestionById("1");
        assertNotNull(question);
        assertEquals("1", question.getId());
    }

    @Test
    @Transactional
    void createQuestionPersistsToDb() {
        // Arrange: ein FormDTO ohne id
        QuestionFormDTO form = new QuestionFormDTO(
                "Wie heisst die Hauptstadt der Schweiz?",
                "Geografie",
                "leicht",
                List.of("Bern", "Zürich", "Genf", "Basel"),
                "Bern"
        );

        // Act: Frage über den Service erstellen
        Question saved = questionService.createQuestion(form);

        // Assert: Der Server hat eine id vergeben ...
        assertNotNull(saved.getId());
        // ... und die Frage ist über ihre id auffindbar
        assertTrue(questionRepository.findById(saved.getId()).isPresent());

        // Kein Aufräumen nötig: @Transactional rollt am Ende automatisch zurück.
    }

}