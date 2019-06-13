package app.ui.admin.controller;

import app.dto.user.StatusName;
import app.dto.user.UserStatus;
import app.ui.admin.service.UserStatusService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;


@Component
public class UserStatusController {

    private final UserStatusService service;

    public UserStatusController(UserStatusService service) {
        this.service = service;
    }

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField requiredPointsField;

    @FXML
    private TextField maximumPointsField;

    @FXML
    private TextField discountField;

    @FXML
    private Label messageLabel;

    @FXML
    private void saveAction() {
        UserStatus userStatus = new UserStatus();
        userStatus.setId(idField.getText().isEmpty() ? null : Long.valueOf(idField.getText()));

        if (StatusName.REGULAR.name().equalsIgnoreCase(nameField.getText()))
            userStatus.setName(StatusName.REGULAR);
        else if (StatusName.GOLD.name().equalsIgnoreCase(nameField.getText()))
            userStatus.setName(StatusName.GOLD);
        else if (StatusName.DIAMOND.name().equalsIgnoreCase(nameField.getText()))
            userStatus.setName(StatusName.DIAMOND);
        else {
            messageLabel.setText("Status name is invalid");
            return;
        }

        userStatus.setRequiredPoints(Integer.parseInt(requiredPointsField.getText()));
        userStatus.setMaximumPoints(Integer.parseInt(maximumPointsField.getText()));
        userStatus.setDiscount(Float.parseFloat(discountField.getText()));

        if (service.save(userStatus))
            messageLabel.setText("Saved");
        else
            messageLabel.setText("Invalid input");
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
    private void findAllAction() {
        service.findAll();
    }
}
