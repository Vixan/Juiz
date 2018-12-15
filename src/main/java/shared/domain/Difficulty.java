package shared.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table
public class Difficulty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DIFFICULTY_ID")
    private Integer id;

    @Column(length = 32)
    private String name;

    @Column
    private Float modifier;

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
