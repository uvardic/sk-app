package app.ui.admin.service.impl;

import app.dto.user.User;
import app.dto.user.UserRole;
import app.dto.user.UserStatus;
import app.mapper.user.UserMapper;
import app.token.service.TokenService;
import app.ui.admin.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final WebClient.Builder webClient;

    private final UserMapper userMapper;

    private final TokenService tokenService;

    public UserServiceImpl(WebClient.Builder webClient, UserMapper userMapper, TokenService tokenService) {
        this.webClient = webClient;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    @Override
    public boolean save(User user) {
        User response = webClient.build()
                .post()
                .uri("http://user-service/service/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(user))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> Mono.empty())
                .bodyToMono(User.class)
                .block();

        return response != null;
    }

    @Override
    public UserRole findUserRole(Long id) {
        return webClient.build()
                .get()
                .uri("http://user-service/service/userRole/" + id)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .bodyToMono(UserRole.class)
                .block();
    }

    @Override
    public UserStatus findUserStatus(Long id) {
        return webClient.build()
                .get()
                .uri("http://user-service/service/userStatus/" + id)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .bodyToMono(UserStatus.class)
                .block();
    }

    @Override
    public boolean delete(Long id) {
        Object response = webClient.build()
                .delete()
                .uri("http://user-service/service/user/delete/" + id)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                .bodyToMono(Map.class)
                .block();

        return response != null;
    }

    @Override
    public void ban(Long id) {
        Long adminId = Long.valueOf((Integer) tokenService.parseToken(tokenService.readToken()).get("id"));

        webClient.build()
                .put()
                .uri(String.format("http://user-service/service/user/ban/%d/%d", id, adminId))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findAll() {
        System.out.println("=======User=======");

        userMapper.map(webClient.build()
                .get()
                .uri("http://user-service/service/user/all")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .bodyToMono(LinkedHashSet.class)
                .block())
                .forEach(System.out::println);
    }
}
