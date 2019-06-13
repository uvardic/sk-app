package app.ui.admin.service.impl;

import app.ui.admin.service.AdminService;
import app.ui.stage.StageListener;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final StageListener stageListener;

    private final Resource sideMenuParent;

    private final Resource userParent;

    public AdminServiceImpl(StageListener stageListener,
                            @Value("classpath:/ui/fxml/admin/sideMenu.fxml") Resource sideMenuParent,
                            @Value("classpath:/ui/fxml/admin/user.fxml") Resource userParent) {
        this.stageListener = stageListener;
        this.sideMenuParent = sideMenuParent;
        this.userParent = userParent;
    }

    @Override
    public void initializeSideMenu(Pane sideMenuPane) {
        sideMenuPane.getChildren().clear();
        sideMenuPane.getChildren().add(stageListener.loadResource(sideMenuParent));
    }

    @Override
    public void initializeContentPane(Pane contentPane) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(stageListener.loadResource(userParent));
    }
}
