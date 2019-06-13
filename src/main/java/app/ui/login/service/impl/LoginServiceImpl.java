package app.ui.login.service.impl;

import app.dto.user.RoleName;
import app.ui.login.service.LoginService;
import app.ui.stage.StageListener;
import app.token.domain.TokenRequest;
import app.token.domain.TokenResponse;
import app.token.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LoginServiceImpl implements LoginService {

    private final StageListener stageListener;

    private final WebClient.Builder webClient;

    private final TokenService tokenService;

    private final Resource registerParent;

    private final Resource indexParent;

    private final Resource adminParent;

    public LoginServiceImpl(StageListener stageListener, WebClient.Builder webClient, TokenService tokenService,
                            @Value("classpath:/ui/fxml/register.fxml") Resource registerParent,
                            @Value("classpath:/ui/fxml/index.fxml") Resource indexParent,
                            @Value("classpath:/ui/fxml/admin/admin.fxml") Resource adminParent) {
        this.stageListener = stageListener;
        this.webClient = webClient;
        this.tokenService = tokenService;
        this.registerParent = registerParent;
        this.indexParent = indexParent;
        this.adminParent = adminParent;
    }

    @Override
    public boolean login(String username, String password) {
        TokenResponse response = webClient.build()
                .post()
                .uri("http://user-service/service/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(new TokenRequest(username, password)))
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                .bodyToMono(TokenResponse.class)
                .block();

        if (response == null)
            return false;

        tokenService.writeToken(response.getToken());

        return true;
    }

    @Override
    public RoleName getLoggedRoleName() {
        String userRoleName = (String) tokenService.parseToken(tokenService.readToken()).get("role");

        return RoleName.ADMIN.name().equals(userRoleName) ? RoleName.ADMIN : RoleName.USER;
    }

    @Override
    public void loadRegisterParent() {
        stageListener.setScene(registerParent);
    }

    @Override
    public void loadIndexParent() {
        stageListener.setScene(indexParent);
    }

    @Override
    public void loadAdminParent() {
        stageListener.setScene(adminParent);
    }
}
