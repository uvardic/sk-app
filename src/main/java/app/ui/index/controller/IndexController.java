package app.ui.index.controller;

import app.dto.movie.Projection;
import app.dto.movie.ProjectionStatus;
import app.ui.index.service.IndexService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.beans.EventHandler;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class IndexController implements Initializable {

    private final IndexService service;

    public IndexController(IndexService service) {
        this.service = service;
    }

    @FXML
    private ComboBox<MovieComboboxPair> movieComboBox;

    @FXML
    private ComboBox<CinemaComboboxPair> cinemaComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service.findAllMovies().stream()
                .map(movie -> new MovieComboboxPair(movie.getId(), movie.getName()))
                .forEach(movieComboboxPair -> movieComboBox.getItems().add(movieComboboxPair));

        service.findAllCinemas().stream()
                .map(cinema -> new CinemaComboboxPair(cinema.getId(), "Cinema - " + cinema.getCinemaNumber()))
                .forEach(cinemaComboboxPair -> cinemaComboBox.getItems().add(cinemaComboboxPair));
    }

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label messageLabel;

    @FXML
    private VBox firstProjectionContainer;

    @FXML
    private VBox secondProjectionContainer;

    @FXML
    private void findProjectionsAction() {
        if (movieComboBox.getSelectionModel().getSelectedItem() == null) {
            messageLabel.setText("Select a movie first!");
            return;
        }

        if (cinemaComboBox.getSelectionModel().getSelectedItem() == null) {
            messageLabel.setText("Select a cinema first!");
            return;
        }

        List<Projection> projections = service.findProjections(movieComboBox.getSelectionModel().getSelectedItem().id,
                cinemaComboBox.getSelectionModel().getSelectedItem().id,
                getDateAsString());

        firstProjectionContainer.getChildren().clear();
        secondProjectionContainer.getChildren().clear();

        projections.stream()
                .filter(projection ->  projection.getStatus() == ProjectionStatus.HAS_ROOM)
                .forEach(this::addProjectionLabel);

        projections.stream()
                .filter(projection ->  projection.getStatus() == ProjectionStatus.FULL)
                .forEach(this::addFullProjectionLabel);
    }

    private String getDateAsString() {
        LocalDate localDate = datePicker.getValue() == null ? LocalDate.now() : datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Date.from(instant));
    }

    private void addProjectionLabel(Projection projection) {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(createProjectionLabel(projection, "projection"),
                createDateLabel(projection, "projection"));

        firstProjectionContainer.getChildren().add(vBox);
    }

    private void addFullProjectionLabel(Projection projection) {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(createProjectionLabel(projection, "projection_full"),
                createDateLabel(projection, "projection_full"));

        secondProjectionContainer.getChildren().add(vBox);
    }

    private Label createProjectionLabel(Projection projection, String styleClass) {
        Label projectionLabel = new Label();
        projectionLabel.setText("Projection - " + projection.getId());
        projectionLabel.getStyleClass().clear();
        projectionLabel.getStyleClass().add(styleClass);
        projectionLabel.setOnMouseClicked(event -> service.loadReservationParent(projection));
        return projectionLabel;
    }

    private Label createDateLabel(Projection projection, String styleClass) {
        Label dateLabel = new Label();
        dateLabel.setText("Start Date " + projection.getStartDate());
        dateLabel.getStyleClass().clear();
        dateLabel.getStyleClass().add(styleClass);
        return dateLabel;
    }

    @Getter
    @AllArgsConstructor
    private class MovieComboboxPair {

        private Long id;

        private String name;

        @Override
        public String toString() {
            return name;
        }
    }

    @Getter
    @AllArgsConstructor
    private class CinemaComboboxPair {

        private Long id;

        private String name;

        @Override
        public String toString() {
            return name;
        }
    }
}
