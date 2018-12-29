import javafx.application.Application;
import persistence.abstractions.PersistenceContext;
import persistence.hibernate.HbnPersistenceContext;
import presentation.Presenter;

/**
 * The driver class of the application where all the other modules are loaded.<br/>
 * The presentation and persistence level modules are loaded in the main method,
 * starting the JavaFX {@link Application}.
 */
public class Juiz {
    public static void main(String[] args) {
        loadPersistence();
        loadPresentation();
    }

    /**
     * Load the presentation level, starting with the root {@link Presenter} class.
     *
     * @see Application
     */
    private static void loadPresentation() {
        Application.launch(Presenter.class);
    }

    /**
     * Load the persistence level, starting with the {@link PersistenceContext} implementation.<br/>
     * Currently, only the Hibernate implementation is available through {@link HbnPersistenceContext}.
     * The initial data is written to the database if does not exist.
     *
     * @see Application
     */
    private static void loadPersistence() {
        HbnPersistenceContext persistenceContext = new HbnPersistenceContext();
        persistenceContext.initialize();
    }
}
