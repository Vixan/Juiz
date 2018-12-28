package presentation.auth;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.services.AuthenticationService;
import presentation.Navigator;
import shared.domain.User;

public class AuthenticationController {
    public TextField usernameInput;
    public PasswordField passwordInput;
    public Button loginButton;
    public Label errorLabel;
    private AuthenticationService authenticationService = new AuthenticationService();

    public void handleLogin() {
        String username = usernameInput.getText().trim();
        String password = passwordInput.getText();

        User user = authenticationService.login(username, password);
        if (user == null) {
            errorLabel.setVisible(true);
        } else {
            Navigator.getInstance().showDashboard(user);
        }
    }
}
