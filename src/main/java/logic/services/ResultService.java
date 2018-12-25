package logic.services;

import logic.abstractions.Service;
import persistence.hibernate.HbnResultRepository;
import shared.domain.Result;

import java.util.List;

public class ResultService implements Service<Result> {
    private HbnResultRepository resultRepository = new HbnResultRepository();

    @Override
    public Result getById(Integer resultId) {
        resultRepository.openCurrentSession();
        Result result = resultRepository.getById(resultId);
        resultRepository.closeCurrentSession();

        return result;
    }

    @Override
    public List<Result> getAll() {
        resultRepository.openCurrentSession();
        List<Result> results = resultRepository.getAll();
        resultRepository.closeCurrentSession();

        return results;
    }

    public List<Result> getByUserId(Integer userId) {
        resultRepository.openCurrentSession();
        List<Result> results = resultRepository.getByUserId(userId);
        resultRepository.closeCurrentSession();

        return results;
    }

    @Override
    public void add(Result result) {
        resultRepository.openCurrentSessionWithTransaction();
        resultRepository.add(result);
        resultRepository.closeCurrentSessionWithTransaction();
    }

    @Override
    public void update(Result result) {
        resultRepository.openCurrentSessionWithTransaction();
        if (resultRepository.getById(result.getId()) != null) {
            resultRepository.update(result);
        }
        resultRepository.closeCurrentSessionWithTransaction();
    }

    @Override
    public void delete(Integer resultId) {
        resultRepository.openCurrentSessionWithTransaction();
        Result result = resultRepository.getById(resultId);
        if (result != null) {
            resultRepository.delete(result);
        }
        resultRepository.closeCurrentSessionWithTransaction();
    }
}
