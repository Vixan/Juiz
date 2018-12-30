package presentation.auth;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.services.AuthenticationService;
import presentation.Navigator;
import shared.domain.User;
import  presentation.dashboard.DashboardController;

/**
 * The controller bound to the {@link User} authentication view.
 * Any logic regarding the view like event handling is coded in this class.
 */
public class AuthenticationController {
    public TextField usernameInput;
    public PasswordField passwordInput;
    public Button loginButton;
    public Label errorLabel;
    private AuthenticationService authenticationService = new AuthenticationService();

    /**
     * Try to sign in the {@link User} with the provided name and password
     * from the view. If authentication succeeded, display the dashboard view,
     * otherwise show an error message.
     *
     * @see DashboardController
     * @see Navigator
     * @see AuthenticationService
     */
    public void handleLogin() {
        String username = usernameInput.getText().trim();
        String password = passwordInput.getText();

        User user = authenticationService.signIn(username, password);
        if (user == null) {
            errorLabel.setVisible(true);
        } else {
            Navigator.getInstance().showDashboard(user);
        }
    }
}
