package bg.trivia.mapper;

import bg.trivia.model.dtos.QuestionDTO;
import bg.trivia.model.entities.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionDTO toDTO(Question question);
}
