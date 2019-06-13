package app.ui.admin.service.impl;

import app.ui.admin.controller.AdminController;
import app.ui.admin.service.SideMenuService;
import app.ui.stage.StageListener;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class SideMenuServiceImpl implements SideMenuService {

    private final StageListener stageListener;

    private final AdminController adminController;

    private final Resource userParent;

    private final Resource userStatusParent;

    private final Resource projectionParent;

    private final Resource movieParent;

    private final Resource cinemaParent;

    public SideMenuServiceImpl(StageListener stageListener, AdminController adminController,
                               @Value("classpath:/ui/fxml/admin/user.fxml") Resource userParent,
                               @Value("classpath:/ui/fxml/admin/userStatus.fxml") Resource userStatusParent,
                               @Value("classpath:/ui/fxml/admin/projection.fxml") Resource projectionParent,
                               @Value("classpath:/ui/fxml/admin/movie.fxml") Resource movieParent,
                               @Value("classpath:/ui/fxml/admin/cinema.fxml") Resource cinemaParent) {
        this.stageListener = stageListener;
        this.adminController = adminController;
        this.userParent = userParent;
        this.userStatusParent = userStatusParent;
        this.projectionParent = projectionParent;
        this.movieParent = movieParent;
        this.cinemaParent = cinemaParent;
    }

    @Override
    public void loadUserParent() {
        adminController.getContentPane().getChildren().clear();
        adminController.getContentPane().getChildren().add(stageListener.loadResource(userParent));
    }

    @Override
    public void loadUserStatusParent() {
        adminController.getContentPane().getChildren().clear();
        adminController.getContentPane().getChildren().add(stageListener.loadResource(userStatusParent));
    }

    @Override
    public void loadProjectionParent() {
        adminController.getContentPane().getChildren().clear();
        adminController.getContentPane().getChildren().add(stageListener.loadResource(projectionParent));
    }

    @Override
    public void loadMovieParent() {
        adminController.getContentPane().getChildren().clear();
        adminController.getContentPane().getChildren().add(stageListener.loadResource(movieParent));
    }

    @Override
    public void loadCinemaParent() {
        adminController.getContentPane().getChildren().clear();
        adminController.getContentPane().getChildren().add(stageListener.loadResource(cinemaParent));
    }
}
