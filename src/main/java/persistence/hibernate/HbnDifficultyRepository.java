package persistence.hibernate;

import persistence.abstractions.DifficultyRepository;
import shared.domain.Difficulty;

public class HbnDifficultyRepository extends HbnRepository<Difficulty> implements DifficultyRepository {
    public HbnDifficultyRepository() {
        super(Difficulty.class);
    }
}