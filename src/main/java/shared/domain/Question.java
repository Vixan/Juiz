package shared.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * The {@link Quiz} question model and database {@link Entity}.<br/>
 * A question is comprised of answers.
 *
 * @see Answer
 */
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_id")
    private Integer id;

    @Column(name = "question_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<Answer> answers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }
}
