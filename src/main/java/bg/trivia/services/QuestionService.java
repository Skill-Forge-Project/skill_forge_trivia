package bg.trivia.services;

import bg.trivia.exceptions.TooManyRequestsException;
import bg.trivia.model.dtos.QuestionDTO;
import bg.trivia.model.dtos.UpdateQuestionDTO;
import bg.trivia.model.dtos.UserRequestDTO;
import bg.trivia.model.entities.postgres.User;
import bg.trivia.model.entities.mongo.Question;
import bg.trivia.model.views.QuestionVIEW;
import bg.trivia.repositories.postgres.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final ModelMapper mapper;
    private final ReportService reportService;
    private Set<String> cachedCollectionNames;

    @Autowired
    public QuestionService(MongoTemplate mongoTemplate, UserRepository userRepository, ObjectMapper objectMapper, ModelMapper mapper, ReportService reportService) {
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.mapper = mapper;
        this.reportService = reportService;
    }

    public void createQuestion(QuestionDTO questionDTO) {
        Question question = mapper.map(questionDTO, Question.class);
        mongoTemplate.save(question, questionDTO.getTechnology() + "-questions");
    }

    public List<QuestionVIEW> get15Questions(UserRequestDTO userRequestDTO) throws JsonProcessingException {
        if (!userAlreadyRequestToPlay(userRequestDTO)) {
            throw new TooManyRequestsException("You have already played trivia today. Please try again tomorrow.");
        }

        List<String> difficulties = List.of("Easy", "Medium", "Hard");
        List<QuestionVIEW> questions = difficulties.stream()
                .flatMap(difficulty -> fetchQuestionsByDifficulty(userRequestDTO.getTechnology(), difficulty, difficulty.equals("Hard") ? 3 : 5).stream())
                .collect(Collectors.toList());

        questions.addAll(fetchCommonQuestions());
        saveUser(userRequestDTO, questions);
        return questions;
    }

    public void updateQuestion(UpdateQuestionDTO questionDTO) {
        Query query = new Query().addCriteria(Criteria.where("_id").is(questionDTO.getId()));

        getCollectionNames().forEach(collectionName -> {
            List<Question> questions = mongoTemplate.find(query, Question.class, collectionName);
            if (!questions.isEmpty()) {
                Question question = mapper.map(questionDTO, Question.class);
                mongoTemplate.save(question, collectionName);
                reportService.updateReport(question.getId());
            }
        });
    }

    private void saveUser(UserRequestDTO userRequestDTO, List<QuestionVIEW> questions) throws JsonProcessingException {
        User user = new User();
        user.setStartedAt(LocalDate.now());
        user.setUserId(userRequestDTO.getUserId());
        user.setQuestions(objectMapper.writeValueAsString(questions));
        userRepository.save(user);
    }

    private List<QuestionVIEW> fetchQuestionsByDifficulty(String technology, String difficulty, int sampleSize) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("difficulty").is(difficulty)),
                Aggregation.sample(sampleSize)
        );
        return fetchAndMapQuestions(aggregation, technology + "-questions");
    }

    private List<QuestionVIEW> fetchCommonQuestions() {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.sample(2));
        return fetchAndMapQuestions(aggregation, "common-questions");
    }

    private List<QuestionVIEW> fetchAndMapQuestions(Aggregation aggregation, String collection) {
        AggregationResults<Question> results = mongoTemplate.aggregate(aggregation, collection, Question.class);
        return results.getMappedResults().stream()
                .map(question -> mapper.map(question, QuestionVIEW.class))
                .collect(Collectors.toList());
    }

    private boolean userAlreadyRequestToPlay(UserRequestDTO userRequestDTO) {
        return userRepository.findUserByUserIdAndStartedAtToday(userRequestDTO.getUserId()).isEmpty();
    }

    private Set<String> getCollectionNames() {
        if (cachedCollectionNames == null) {
            cachedCollectionNames = mongoTemplate.getCollectionNames().stream()
                    .filter(name -> !name.startsWith("system."))
                    .collect(Collectors.toSet());
        }
        return cachedCollectionNames;
    }
}
