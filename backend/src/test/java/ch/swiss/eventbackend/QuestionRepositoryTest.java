package ch.swiss.eventbackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.swiss.eventbackend.model.Question;
import ch.swiss.eventbackend.repository.QuestionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test 
    void seederHasDbFilled() { 
        long anzahl = questionRepository.count();  
        assertTrue(anzahl > 0); 
    } 

    @Test 
    void questionCanBeLoaded() { 
        Optional<Question> frage = questionRepository.findById("1");   
        assertTrue(frage.isPresent()); 
    } 

}