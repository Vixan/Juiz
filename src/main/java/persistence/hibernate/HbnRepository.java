package persistence.hibernate;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import persistence.abstractions.Repository;

import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.File;
import java.util.List;

/**
 * The {@link Hibernate} implementation of the {@link Repository}.
 * Hibernate uses sessions and transactions to retrieve and persist data in the
 * database.
 * <br/><br/>
 * By default, the <a href="http://www.h2database.com/">H2 Database Engine</a>
 * is used with it's corresponding driver.
 *
 * @see Session
 * @see Transaction
 * @see H2Dialect
 */
public class HbnRepository<T> implements Repository<T> {
    private Session currentSession;
    private Transaction currentTransaction;
    private Class<T> type;

    /**
     * Construct a Hibernate repository object for a specific database {@link Entity}.
     *
     * @param type the database entity used.
     */
    HbnRepository(Class<T> type) {
        this.type = type;
    }

    /**
     * Retrieve the current Hibernate {@link Session} object
     *
     * @return the current Hibernate session object.
     */
    Session getCurrentSession() {
        return currentSession;
    }


    /**
     * Create and retrieve a Hibernate {@link SessionFactory} object,
     * used to create sessions. The session factory uses the configurations specified
     * in the <i>hibernate.cfg.xml</i> file.
     *
     * @param annotationClass the annotation class used.
     * @return a session factory object.
     * @see Session
     */
    private static SessionFactory getSessionFactory(Class annotationClass) {
        File hibernateConfig = new File("src/main/resources/config/hibernate.cfg.xml");

        return new Configuration()
                .configure(hibernateConfig)
                .addAnnotatedClass(annotationClass)
                .buildSessionFactory();
    }

    /**
     * Create and open the current session.
     *
     * @see Session
     * @see SessionFactory
     */
    public void openCurrentSession() {
        currentSession = getSessionFactory(type).openSession();
    }

    /**
     * Close the current {@link Session}, removing everything from it's context,
     * meaning that any data saved in the context will be lost if not persisted
     * in the database.
     */
    public void closeCurrentSession() {
        currentSession.close();
    }

    @Override
    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> quizRoot = criteriaQuery.from(type);
        criteriaQuery.select(quizRoot);

        return currentSession.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Create and open the current session and begin a Hibernate {@link Transaction}.
     * Data saved in the {@link Session} context is persisted in the database using a transaction
     * object.
     *
     * @see SessionFactory
     */
    public void openCurrentSessionWithTransaction() {
        currentSession = getSessionFactory(type).openSession();
        currentTransaction = currentSession.beginTransaction();
    }

    /**
     * Close the current {@link Session} and commit the context data in the database,
     * finishing the {@link Transaction}.
     */
    public void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    @Override
    public T getById(Integer id) {
        return currentSession.get(type, id);
    }

    @Override
    public void add(T entity) {
        currentSession.save(entity);
    }

    @Override
    public void update(T entity) {
        currentSession.update(entity);
    }

    @Override
    public void delete(T entity) {
        currentSession.delete(entity);
    }
}
