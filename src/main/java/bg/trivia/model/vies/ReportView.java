package bg.trivia.model.vies;


import bg.trivia.model.dtos.QuestionDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReportView {

    private int id;
    private QuestionDTO question;
    private boolean resolved;

}
