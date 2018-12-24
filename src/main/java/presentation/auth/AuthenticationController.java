package presentation.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logic.services.AuthenticationService;
import presentation.dashboard.DashboardController;
import shared.domain.User;
import shared.utils.ConfigProperties;

import java.io.IOException;

public class AuthenticationController {
    public TextField usernameInput;
    public PasswordField passwordInput;
    public Button loginButton;
    public Label errorLabel;
    private ConfigProperties configProperties = ConfigProperties.getInstance();
    private AuthenticationService authenticationService = new AuthenticationService();

    public void handleLogin(ActionEvent event) {
        String username = usernameInput.getText().trim();
        String password = passwordInput.getText();
        errorLabel.setVisible(false);

        User user = authenticationService.login(username, password);
        if (user == null) {
            errorLabel.setVisible(true);
        } else {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            showDashboard(user);
        }
    }

    private void showDashboard(User user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/presentation/dashboard/dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            int windowWidth = 800;
            int windowHeight = 600;

            if (configProperties != null) {
                windowWidth = configProperties.getWindowWidth();
                windowHeight = configProperties.getWindowHeight();
            }

            DashboardController dashboardController = fxmlLoader.getController();
            dashboardController.setUser(user);

            Stage stage = new Stage();
            stage.setTitle("Juiz Dashboard");
            stage.setScene(scene);
            stage.setMinHeight(windowHeight);
            stage.setMinWidth(windowWidth);
            stage.getIcons().add(new Image("/presentation/assets/juiz.icon.png"));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
