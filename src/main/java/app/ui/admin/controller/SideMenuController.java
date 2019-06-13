package app.ui.admin.controller;

import app.ui.admin.service.SideMenuService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;


@Component
public class SideMenuController {

    private final SideMenuService service;

    public SideMenuController(SideMenuService service) {
        this.service = service;
    }

    @FXML
    private Label userOption;

    @FXML
    private Label userStatusOption;

    @FXML
    private Label projectionOption;

    @FXML
    private Label movieOption;

    @FXML
    private Label cinemaOption;

    @FXML
    private void loadUserParent() {
        deselectAll();
        userOption.getStyleClass().clear();
        userOption.getStyleClass().add("selected_menu_option");
        service.loadUserParent();
    }

    @FXML
    private void loadUserStatusParent() {
        deselectAll();
        userStatusOption.getStyleClass().clear();
        userStatusOption.getStyleClass().add("selected_menu_option");
        service.loadUserStatusParent();
    }

    @FXML
    private void loadProjectionParent() {
        deselectAll();
        projectionOption.getStyleClass().clear();
        projectionOption.getStyleClass().add("selected_menu_option");
        service.loadProjectionParent();
    }

    @FXML
    private void loadMovieParent() {
        deselectAll();
        movieOption.getStyleClass().clear();
        movieOption.getStyleClass().add("selected_menu_option");
        service.loadMovieParent();
    }

    @FXML
    private void loadCinemaParent() {
        deselectAll();
        cinemaOption.getStyleClass().clear();
        cinemaOption.getStyleClass().add("selected_menu_option");
        service.loadCinemaParent();
    }

    private void deselectAll() {
        userOption.getStyleClass().clear();
        userOption.getStyleClass().add("menu_option");
        userStatusOption.getStyleClass().clear();
        userStatusOption.getStyleClass().add("menu_option");
        projectionOption.getStyleClass().clear();
        projectionOption.getStyleClass().add("menu_option");
        movieOption.getStyleClass().clear();
        movieOption.getStyleClass().add("menu_option");
        cinemaOption.getStyleClass().clear();
        cinemaOption.getStyleClass().add("menu_option");
    }

}
