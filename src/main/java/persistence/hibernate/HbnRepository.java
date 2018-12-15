package persistence.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import persistence.abstractions.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
        SessionFactory sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(annotationClass)
                .buildSessionFactory();

        return sessionFactory;
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory(type).openSession();

        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        List<T> collection = getCurrentSession().createQuery(criteriaQuery).getResultList();

        return collection;
    }

    public T getById(Integer id) {
        return getCurrentSession().get(type, id);
    }

    public void add(T entity) {
        getCurrentSession().save(entity);
    }

    public void update(T entity) {
        getCurrentSession().update(entity);
    }

    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }
}
