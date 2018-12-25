package shared.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "result_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Transient
    private List<Boolean> answersValidationList;

    @Column(name = "result_answers_validation")
    private String answersValidation;

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Boolean> getAnswersValidationList() {
        return answersValidationList;
    }

    public void setAnswersValidationList(List<Boolean> answersValidationList) {
        this.answersValidationList = answersValidationList;
    }

    public void setAnswersValidation(String answersValidation) {
        this.answersValidation = answersValidation;
    }

    public String getAnswersValidation() {
        return answersValidation;
    }
}
