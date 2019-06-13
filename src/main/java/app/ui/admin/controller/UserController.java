package app.ui.admin.controller;

import app.dto.user.User;
import app.ui.admin.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @FXML
    private TextField idField;

    @FXML
    private TextField userRoleIdField;

    @FXML
    private TextField userStatusIdField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private Label messageLabel;

    @FXML
    private void saveAction() {
        User user = new User();
        user.setId(idField.getText().isEmpty() ? null : Long.valueOf(idField.getText()));
        user.setUserRole(service.findUserRole(Long.valueOf(userRoleIdField.getText())));
        user.setUserStatus(service.findUserStatus(Long.valueOf(userStatusIdField.getText())));
        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText());
        user.setEmail(emailField.getText());
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());

        if (service.save(user))
            messageLabel.setText("Saved");
        else
            messageLabel.setText("Error, invalid input!");
    }

    @FXML
    private TextField idDeleteField;

    @FXML
    private Label deleteMessageLabel;

    @FXML
    private void deleteAction() {
        if (idDeleteField.getText().isEmpty()) {
            deleteMessageLabel.setText("Id field can't be blank");
            return;
        }

        service.delete(Long.valueOf(idDeleteField.getText()));
    }

    @FXML
    private TextField idBanField;

    @FXML
    private Label banMessageLabel;

    @FXML
    private void banAction() {
        if (idBanField.getText().isEmpty()) {
            banMessageLabel.setText("Id field can't be blank");
            return;
        }

        service.ban(Long.valueOf(idBanField.getText()));
    }

    @FXML
    private void findAllAction() {
        service.findAll();
    }

}
