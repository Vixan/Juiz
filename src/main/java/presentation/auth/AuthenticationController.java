package presentation.auth;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.services.AuthenticationService;
import shared.domain.User;

public class AuthenticationController {
    public TextField usernameInput;
    public PasswordField passwordInput;
    public Button loginButton;
    private AuthenticationService authenticationService = new AuthenticationService();

    public AuthenticationController() {

    }

    public void handleLogin(ActionEvent event) {
        String username = usernameInput.getText().trim();
        String password = passwordInput.getText();

        User user = authenticationService.login(username, password);
        if (user == null) {
            System.out.println("invalid username or password");
        } else {
            System.out.println("valid");
        }
    }
}
