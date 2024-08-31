package bg.trivia.controllers;

import bg.trivia.entities.Question;
import bg.trivia.services.JavaQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/java")
public class JavaQuestionController {

    private final JavaQuestionService javaQuestionService;

    @Autowired
    public JavaQuestionController(JavaQuestionService javaQuestionService) {
        this.javaQuestionService = javaQuestionService;
    }

    @RequestMapping("/easy")
    public ResponseEntity<?> get10EasyQuestions() {
        List<? extends Question> javaQuestionService10EasyQuestions = javaQuestionService.get10EasyQuestions();
        return ResponseEntity.ok().body(javaQuestionService10EasyQuestions);
    }
}
