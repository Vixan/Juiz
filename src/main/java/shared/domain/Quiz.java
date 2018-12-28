package shared.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * The quiz model and database {@link Entity}.<br/>
 * A quiz can be taken in a certain time limit and is comprised of questions.
 * Each quiz is part of a {@link Category}.
 *
 * @see Question
 */
@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quiz_id")
    private Integer id;

    @Column(name = "quiz_name")
    private String name;

    @Column(name = "quiz_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private Set<Question> questions;

    @Column(name = "quiz_time_limit")
    private Integer timeLimit;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }
}
