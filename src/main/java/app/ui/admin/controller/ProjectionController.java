package app.ui.admin.controller;

import app.dto.movie.Projection;
import app.dto.movie.ProjectionStatus;
import app.ui.admin.service.ProjectionService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class ProjectionController {

    private final ProjectionService service;

    public ProjectionController(ProjectionService service) {
        this.service = service;
    }

    @FXML
    private TextField idField;

    @FXML
    private TextField movieIdField;

    @FXML
    private TextField cinemaIdField;

    @FXML
    private DatePicker startDateField;

    @FXML
    private TextField ticketPriceField;

    @FXML
    private TextField statusField;

    @FXML
    private TextField numberOfReservationsField;

    @FXML
    private Label messageLabel;

    @FXML
    private void saveAction() {
        Projection projection = new Projection();
        projection.setId(idField.getText().isEmpty() ? null : Long.valueOf(idField.getText()));
        projection.setMovie(service.findMovie(Long.valueOf(movieIdField.getText())));
        projection.setCinema(service.findCinema(Long.valueOf(cinemaIdField.getText())));
        projection.setStartDate(getDate());
        projection.setTicketPrice(Float.parseFloat(ticketPriceField.getText()));

        if (ProjectionStatus.HAS_ROOM.name().equalsIgnoreCase(statusField.getText()))
            projection.setStatus(ProjectionStatus.HAS_ROOM);
        else if (ProjectionStatus.FULL.name().equalsIgnoreCase(statusField.getText()))
            projection.setStatus(ProjectionStatus.FULL);
        else {
            messageLabel.setText("Invalid projection status");
            return;
        }

        projection.setNumberOfReservations(Integer.parseInt(numberOfReservationsField.getText()));

        if (service.save(projection))
            messageLabel.setText("Saved");
        else
            messageLabel.setText("Invalid input!");
    }

    private String getDate() {
        LocalDate localDate = startDateField.getValue() == null ? LocalDate.now() : startDateField.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Date.from(instant));
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
