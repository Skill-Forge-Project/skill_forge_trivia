package bg.trivia.services;

import bg.trivia.entities.Question;
import bg.trivia.utils.GetQuestions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JavaQuestionService {


    public List<? extends Question>  get10EasyQuestions() {
        return GetQuestions.get10Questions("java");
    }

}
