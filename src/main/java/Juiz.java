import javafx.application.Application;
import persistence.hibernate.HbnPersistenceContext;
import presentation.Presenter;

public class Juiz {
    public static void main(String[] args) {
        loadPersistence();
        loadPresentation();
    }

    private static void loadPresentation() {
        Application.launch(Presenter.class);
    }

    private static void loadPersistence() {
        HbnPersistenceContext persistenceContext = new HbnPersistenceContext();
        persistenceContext.initializeData();
    }
}
