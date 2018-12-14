import javafx.application.Application;
import presentation.Presenter;

public class Juiz {
    public static void main(String[] args) {
        loadPresentation();
    }

    private static void loadPresentation() {
        Application.launch(Presenter.class);
    }
}
