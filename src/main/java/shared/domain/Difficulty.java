package shared.domain;

import javax.persistence.*;

/**
 * The difficulty level model and database {@link Entity}.<br/>
 * Each {@link Quiz} can be taken in different levels of difficulty,
 * which can influence the outcome.
 * <br/><br/>
 * For example: each difficulty level has a time modifier.
 */
@Entity
@Table(name = "difficulty")
public class Difficulty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "difficulty_id")
    private Integer id;

    @Column(name = "difficulty_name")
    private String name;

    @Column(name = "difficulty_modifier")
    private Float modifier;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getModifier() {
        return modifier;
    }

    public void setModifier(Float modifier) {
        this.modifier = modifier;
    }
}
