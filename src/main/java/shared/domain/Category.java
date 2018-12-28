package shared.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * The category model and database {@link Entity}.<br/>
 * Quizzes are divided in categories and can be certain school subjects like "Astronomy".
 * @see Quiz
 */
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Integer id;

    @Column(name="category_name")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Quiz> quizzes;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
