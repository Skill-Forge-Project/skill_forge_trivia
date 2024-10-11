package bg.trivia.model.views;


import bg.trivia.model.dtos.QuestionDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReportView {

    private int id;
    private String userId;
    private QuestionVIEW question;
    private boolean resolved;

}
