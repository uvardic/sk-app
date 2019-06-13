package app.ui.stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageListener implements ApplicationListener<StageReadyEvent> {

    private final String applicationTitle;

    private final Resource loginParent;

    private final ApplicationContext context;

    public StageListener(@Value("${spring.application.ui.title}") String applicationTitle,
                         @Value("classpath:/ui/fxml/login.fxml") Resource loginParent,
                         ApplicationContext context) {
        this.applicationTitle = applicationTitle;
        this.loginParent = loginParent;
        this.context = context;
    }

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        this.stage = stageReadyEvent.getStage();

        stage.setTitle(applicationTitle);
        stage.setResizable(false);
        setScene(loginParent);
        stage.show();
    }

    public void setResizable(boolean resizable) {
        stage.setResizable(resizable);
    }

    public void setScene(Resource resource) {
        stage.setScene(new Scene(loadResource(resource)));
    }

    public Parent loadResource(Resource resource) {
        try {
            FXMLLoader loader = new FXMLLoader(resource.getURL());

            loader.setControllerFactory(context::getBean);
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException(String.format("Failed to load loginParent: %s", resource));
    }

    public FXMLLoader getFXMLLoaderFor(Resource resource) {
        try {
            FXMLLoader loader = new FXMLLoader(resource.getURL());

            loader.setControllerFactory(context::getBean);
            return loader;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        throw new RuntimeException(String.format("Failed to load loginParent: %s", resource));
    }

}
