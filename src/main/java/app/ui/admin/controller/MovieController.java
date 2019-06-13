package app.ui.admin.controller;

import app.dto.movie.Movie;
import app.ui.admin.service.MovieService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField genreField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField lengthField;

    @FXML
    private TextField actorsField;

    @FXML
    private Label messageLabel;

    @FXML
    private void saveAction() {
        Movie movie = new Movie();
        movie.setId(idField.getText().isEmpty() ? null : Long.valueOf(idField.getText()));
        movie.setName(nameField.getText());
        movie.setGenre(genreField.getText());
        movie.setDescription(descriptionField.getText());
        movie.setLength(Integer.parseInt(lengthField.getText()));
        movie.setActors(parseActors());

        if (service.save(movie))
            messageLabel.setText("Saved");
        else
            messageLabel.setText("Invalid input");
    }

    private List<String> parseActors() {
        return Arrays.stream(actorsField.getText().split(","))
                .collect(Collectors.toList());
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
