package persistence.abstractions;

import shared.domain.Difficulty;

import javax.persistence.Entity;

/**
 * The Difficulty {@link Entity} repository interface with custom behaviour.
 *
 * @see Repository
 * @see Difficulty
 */
public interface DifficultyRepository extends Repository<Difficulty> {
    /**
     * @param difficultyName the name to retrieve the difficulty level by.
     * @return the difficulty level queried.
     */
    Difficulty getByName(String difficultyName);
}
