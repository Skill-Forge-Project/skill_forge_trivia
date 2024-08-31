package bg.trivia.services;

import bg.trivia.entities.Question;
import bg.trivia.utils.GetQuestions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {


    public List<? extends Question> get10Questions(String technology, String difficulty) {
        return GetQuestions.get10EasyQuestions(technology, difficulty);
    }

}
