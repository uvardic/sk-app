package app.ui.admin.service.impl;

import app.dto.user.UserStatus;
import app.mapper.user.UserStatusMapper;
import app.token.service.TokenService;
import app.ui.admin.service.UserStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;
import java.util.Map;

@Service
public class UserStatusServiceImpl implements UserStatusService {

    private final WebClient.Builder webClient;

    private final UserStatusMapper userStatusMapper;

    private final TokenService tokenService;

    public UserStatusServiceImpl(WebClient.Builder webClient, UserStatusMapper userStatusMapper,
                                 TokenService tokenService) {
        this.webClient = webClient;
        this.userStatusMapper = userStatusMapper;
        this.tokenService = tokenService;
    }

    @Override
    public boolean save(UserStatus userStatus) {
        UserStatus response = webClient.build()
                .post()
                .uri("http://user-service/service/userStatus/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .body(BodyInserters.fromObject(userStatus))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> Mono.empty())
                .bodyToMono(UserStatus.class)
                .block();

        return response != null;
    }

    @Override
    public void delete(Long id) {
        webClient.build()
                .delete()
                .uri("http://user-service/service/userStatus/delete/" + id)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                .bodyToMono(Map.class)
                .block();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findAll() {
        System.out.println("=======User Status=======");

        userStatusMapper.map(webClient.build()
                .get()
                .uri("http://user-service/service/userStatus/all")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .bodyToMono(LinkedHashSet.class)
                .block())
                .forEach(System.out::println);
    }
}
