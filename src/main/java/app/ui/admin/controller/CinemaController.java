package app.ui.admin.controller;

import app.dto.movie.Cinema;
import app.ui.admin.service.CinemaService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class CinemaController {

    private final CinemaService service;

    public CinemaController(CinemaService service) {
        this.service = service;
    }

    @FXML
    private TextField idField;

    @FXML
    private TextField cinemaNumberField;

    @FXML
    private TextField numberOfRowsField;

    @FXML
    private TextField numberOfSeatsPerRowField;

    @FXML
    private Label messageLabel;

    @FXML
    private void saveAction() {
        Cinema cinema = new Cinema();
        cinema.setId(idField.getText().isEmpty() ? null : Long.valueOf(idField.getText()));
        cinema.setCinemaNumber(Integer.parseInt(cinemaNumberField.getText()));
        cinema.setNumberOfRows(Integer.parseInt(numberOfRowsField.getText()));
        cinema.setNumberOfSeatsPerRow(Integer.parseInt(numberOfSeatsPerRowField.getText()));

        if (service.save(cinema))
            messageLabel.setText("Saved");
        else
            messageLabel.setText("Invalid input!");
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
