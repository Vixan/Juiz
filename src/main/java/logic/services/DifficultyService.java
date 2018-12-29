package logic.services;

import logic.abstractions.Service;
import persistence.hibernate.HbnDifficultyRepository;
import shared.domain.Difficulty;

import java.util.List;

/**
 * Provides methods for communication with
 * the Hibernate {@link Difficulty} repository.
 *
 * @see Service
 */
public class DifficultyService implements Service<Difficulty> {
    private HbnDifficultyRepository difficultyRepository = new HbnDifficultyRepository();

    @Override
    public Difficulty getById(Integer difficultyId) {
        difficultyRepository.openCurrentSession();
        Difficulty difficulty = difficultyRepository.getById(difficultyId);
        difficultyRepository.closeCurrentSession();

        return difficulty;
    }

    public Difficulty getByName(String difficultyName) {
        difficultyRepository.openCurrentSession();
        Difficulty difficulty = difficultyRepository.getByName(difficultyName);
        difficultyRepository.closeCurrentSession();

        return difficulty;
    }

    @Override
    public List<Difficulty> getAll() {
        difficultyRepository.openCurrentSession();
        List<Difficulty> difficulties = difficultyRepository.getAll();
        difficultyRepository.closeCurrentSession();

        return difficulties;
    }

    @Override
    public void add(Difficulty difficulty) {
        difficultyRepository.openCurrentSessionWithTransaction();
        if (difficultyRepository.getByName(difficulty.getName()) == null) {
            difficultyRepository.add(difficulty);
        }
        difficultyRepository.closeCurrentSessionWithTransaction();
    }

    @Override
    public void update(Difficulty difficulty) {
        difficultyRepository.openCurrentSessionWithTransaction();
        if (difficultyRepository.getById(difficulty.getId()) != null) {
            difficultyRepository.update(difficulty);
        }
        difficultyRepository.closeCurrentSessionWithTransaction();
    }

    @Override
    public void delete(Integer difficultyId) {
        difficultyRepository.openCurrentSessionWithTransaction();
        Difficulty difficulty = difficultyRepository.getById(difficultyId);
        if (difficulty != null) {
            difficultyRepository.delete(difficulty);
        }
        difficultyRepository.closeCurrentSessionWithTransaction();
    }
}
