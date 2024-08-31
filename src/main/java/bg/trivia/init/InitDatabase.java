package bg.trivia.init;

import bg.trivia.entities.CsharpQuestion;
import bg.trivia.entities.JavaQuestion;
import bg.trivia.entities.JavaScriptQuestion;
import bg.trivia.entities.PythonQuestion;
import bg.trivia.repostiories.CsharpQuestionRepository;
import bg.trivia.repostiories.JavaQuestionRepository;
import bg.trivia.repostiories.JavaScriptQuestionRepository;
import bg.trivia.repostiories.PythonQuestionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class InitDatabase {

    private CsharpQuestionRepository csharpQuestionRepository;
    private JavaQuestionRepository javaQuestionRepository;
    private JavaScriptQuestionRepository javaScriptQuestionRepository;
    private PythonQuestionRepository pythonQuestionRepository;

    private ApplicationContext applicationContext;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public InitDatabase(CsharpQuestionRepository csharpQuestionRepository, JavaQuestionRepository javaQuestionRepository, JavaScriptQuestionRepository javaScriptQuestionRepository, PythonQuestionRepository pythonQuestionRepository, ApplicationContext applicationContext) {
        this.csharpQuestionRepository = csharpQuestionRepository;
        this.javaQuestionRepository = javaQuestionRepository;
        this.javaScriptQuestionRepository = javaScriptQuestionRepository;
        this.pythonQuestionRepository = pythonQuestionRepository;
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        if (csharpQuestionRepository.count() == 0) {
            loadQuestions("classpath:files/CSharp1.json", CsharpQuestion.class, csharpQuestionRepository);
            loadQuestions("classpath:files/CSharp2.json", CsharpQuestion.class, csharpQuestionRepository);
            loadQuestions("classpath:files/CSharp3.json", CsharpQuestion.class, csharpQuestionRepository);
        }
        if (javaQuestionRepository.count() == 0) {
            loadQuestions("classpath:files/Java1.json", JavaQuestion.class, javaQuestionRepository);
            loadQuestions("classpath:files/Java2.json", JavaQuestion.class, javaQuestionRepository);
            loadQuestions("classpath:files/Java3.json", JavaQuestion.class, javaQuestionRepository);
        }
        if (javaScriptQuestionRepository.count() == 0) {
            loadQuestions("classpath:files/JavaScript1.json", JavaScriptQuestion.class, javaScriptQuestionRepository);
            loadQuestions("classpath:files/JavaScript2.json", JavaScriptQuestion.class, javaScriptQuestionRepository);
            loadQuestions("classpath:files/JavaScript3.json", JavaScriptQuestion.class, javaScriptQuestionRepository);
        }
        if (pythonQuestionRepository.count() == 0) {
            loadQuestions("classpath:files/Python1.json", PythonQuestion.class, pythonQuestionRepository);
            loadQuestions("classpath:files/Python2.json", PythonQuestion.class, pythonQuestionRepository);
            loadQuestions("classpath:files/Python3.json", PythonQuestion.class, pythonQuestionRepository);
        }
    }

    private <T> void loadQuestions(String filePath, Class<T> questionClass, MongoRepository<T, String> repository) {
        try {
            Resource resource = applicationContext.getResource(filePath);
            InputStream inputStream = resource.getInputStream();
            List<T> questions = objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, questionClass));
            repository.saveAll(questions);
        } catch (MismatchedInputException e) {
            System.err.println("Failed to deserialize: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
