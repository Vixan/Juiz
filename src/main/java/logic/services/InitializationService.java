package logic.services;

import persistence.hibernate.HbnUserRepository;

public class InitializationService {
    public void initDatabaseConnection() {
        HbnUserRepository repository = new HbnUserRepository();
        repository.openCurrentSession();
        repository.closeCurrentSession();
    }
}
