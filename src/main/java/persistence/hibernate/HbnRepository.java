package persistence.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import persistence.abstractions.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.File;
import java.util.List;

public class HbnRepository<T> implements Repository<T> {
    private Session currentSession;
    private Transaction currentTransaction;
    private Class<T> type;

    public HbnRepository(Class<T> type) {
        this.type = type;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    private static SessionFactory getSessionFactory(Class annotationClass) {
        File hibernateConfig = new File("src/main/resources/config/hibernate.cfg.xml");

        return new Configuration()
                .configure(hibernateConfig)
                .addAnnotatedClass(annotationClass)
                .buildSessionFactory();
    }

    public void openCurrentSession() {
        currentSession = getSessionFactory(type).openSession();
    }

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

    public void openCurrentSessionWithTransaction() {
        currentSession = getSessionFactory(type).openSession();
        currentTransaction = currentSession.beginTransaction();
    }

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
