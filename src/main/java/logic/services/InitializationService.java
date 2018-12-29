package logic.services;

import persistence.hibernate.HbnUserRepository;
import persistence.abstractions.Repository;
import org.hibernate.Session;

/**
 * The service responsible for initializing a connection
 * to the database. When the application starts, the connection will
 * be established. Without using this service, the very first
 * query against the database will case a delay for connection
 * initialization.
 *
 * @see HbnUserRepository
 */
public class InitializationService {
    /**
     * Initialize the database connection by opening a {@link Session} and closing
     * it immediately.
     *
     * @see Repository
     */
    public void initDatabaseConnection() {
        HbnUserRepository repository = new HbnUserRepository();
        repository.openCurrentSession();
        repository.closeCurrentSession();
    }
}
