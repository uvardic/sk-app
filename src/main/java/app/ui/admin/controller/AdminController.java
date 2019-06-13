package app.ui.admin.controller;

import app.ui.admin.service.AdminService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AdminController implements Initializable {

    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @FXML
    private Pane sideMenuPane;

    @FXML
    private Pane contentPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service.initializeSideMenu(sideMenuPane);
        service.initializeContentPane(contentPane);
    }

    public Pane getContentPane() {
        return contentPane;
    }
}
