package ch.swiss.eventbackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import ch.swiss.eventbackend.exception.QuestionNotFoundException;
import ch.swiss.eventbackend.model.Question;
import ch.swiss.eventbackend.service.QuestionService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {
    @Autowired
    private MockMvcTester mvc; // wird von @WebMvcTest automatisch konfiguriert

    @MockitoBean
    private QuestionService service; // Ersatz für @MockBean (in SB4 entfernt!)

   @Test
   void getByIdReturns200WithBody() {
       // Arrange
       Question q = new Question();
       q.setId("1");
       q.setText("Wie viele Spieler hat ein Fussballteam auf dem Feld?");
       q.setCategory("Sport");
       q.setDifficulty("leicht");
       q.setAnswers(List.of("11", "9", "7", "5"));
       q.setCorrectAnswer("11");
       given(service.getQuestionById("1")).willReturn(q);


       // Act + Assert
       assertThat(mvc.get().uri("/api/questions/1"))
               .hasStatusOk()
               .bodyJson()
               .extractingPath("$.text")
               .isEqualTo("Wie viele Spieler hat ein Fussballteam auf dem Feld?");
   }

    @Test
    void getByIdReturns404WhenServiceThrows() {
        // Arrange: der Service wirft die Exception
        given(service.getQuestionById("999"))
                .willThrow(new QuestionNotFoundException("999"));

        // Act + Assert: dein GlobalExceptionHandler muss daraus 404 machen
        assertThat(mvc.get().uri("/api/questions/999"))
                .hasStatus(HttpStatus.NOT_FOUND);
    }
}
