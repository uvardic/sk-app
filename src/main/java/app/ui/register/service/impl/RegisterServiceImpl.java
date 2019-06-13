package app.ui.register.service.impl;

import app.dto.user.User;
import app.dto.user.UserRole;
import app.dto.user.UserStatus;
import app.exception.ResourceNotFoundException;
import app.ui.register.service.RegisterService;
import app.ui.stage.StageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final StageListener stageListener;

    private final WebClient.Builder webClient;

    private final Resource loginParent;

    public RegisterServiceImpl(StageListener stageListener, WebClient.Builder webClient,
                               @Value("classpath:/ui/fxml/login.fxml") Resource loginParent) {
        this.stageListener = stageListener;
        this.webClient = webClient;
        this.loginParent = loginParent;
    }

    @Override
    public boolean register(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setUserRole(getDefaultUserRole());
        user.setUserStatus(getDefaultUserStatus());

        User response = webClient.build()
                .post()
                .uri("http://user-service/service/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(user))
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                .bodyToMono(User.class)
                .block();

        return response != null;
    }

    private UserRole getDefaultUserRole() {
        return webClient.build()
                .get()
                .uri("http://user-service/service/userRole/name/USER")
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.error(ResourceNotFoundException::new))
                .bodyToMono(UserRole.class)
                .block();
    }

    private UserStatus getDefaultUserStatus() {
        return webClient.build()
                .get()
                .uri("http://user-service/service/userStatus/name/REGULAR")
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.error(ResourceNotFoundException::new))
                .bodyToMono(UserStatus.class)
                .block();
    }

    @Override
    public void loadLoginParent() {
        stageListener.setScene(loginParent);
    }
}
