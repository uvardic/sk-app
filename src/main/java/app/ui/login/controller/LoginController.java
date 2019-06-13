package app.ui.login.controller;

import app.dto.user.RoleName;
import app.ui.login.service.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class LoginController {

    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void loginAction() {
        if (usernameField.getText() == null || usernameField.getText().isEmpty()) {
            messageLabel.setText("Username field can't be blank!");
            return;
        }

        if (passwordField.getText() == null || passwordField.getText().isEmpty()) {
            messageLabel.setText("Password field can't be blank!");
            return;
        }

        if (service.login(usernameField.getText(), passwordField.getText())) {
            if (service.getLoggedRoleName().equals(RoleName.USER))
                service.loadIndexParent();
            else
                service.loadAdminParent();
        } else
            messageLabel.setText("Invalid username or password!");
    }

    @FXML
    private void registerAction() {
        service.loadRegisterParent();
    }
}
