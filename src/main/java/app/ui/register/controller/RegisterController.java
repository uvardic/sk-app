package app.ui.register.controller;

import app.ui.register.service.RegisterService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class RegisterController {

    private final RegisterService service;

    public RegisterController(RegisterService service) {
        this.service = service;
    }

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void registerAction() {
        if (usernameField.getText() == null || usernameField.getText().isEmpty()) {
            messageLabel.setText("Username field can't be blank!");
            return;
        }

        if (emailField.getText() == null || emailField.getText().isEmpty()) {
            messageLabel.setText("Email field can't be blank!");
            return;
        }

        if (passwordField.getText() == null || passwordField.getText().isEmpty()) {
            messageLabel.setText("Password field can't be blank!");
            return;
        }

        if (passwordField.getText().length() < 8) {
            messageLabel.setText("Password must be at least 8 chars long!");
            return;
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            messageLabel.setText("Passwords don't match!");
            return;
        }

        if (service.register(usernameField.getText(), passwordField.getText(), emailField.getText()))
            service.loadLoginParent();
        else
            messageLabel.setText("Invalid input!");
    }

    @FXML
    private void backAction() {
        service.loadLoginParent();
    }

}
