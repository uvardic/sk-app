package app.ui.admin.service.impl;

import app.dto.movie.Cinema;
import app.mapper.movie.CinemaMapper;
import app.token.service.TokenService;
import app.ui.admin.service.CinemaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;
import java.util.Map;

@Service
public class CinemaServiceImpl implements CinemaService {

    private final WebClient.Builder webClient;

    private final CinemaMapper cinemaMapper;

    private final TokenService tokenService;

    public CinemaServiceImpl(WebClient.Builder webClient, CinemaMapper cinemaMapper,
                             TokenService tokenService) {
        this.webClient = webClient;
        this.cinemaMapper = cinemaMapper;
        this.tokenService = tokenService;
    }

    @Override
    public boolean save(Cinema cinema) {
        Cinema response = webClient.build()
                .post()
                .uri("http://movie-service/service/cinema/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .body(BodyInserters.fromObject(cinema))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> Mono.empty())
                .bodyToMono(Cinema.class)
                .block();

        return response != null;
    }

    @Override
    public void delete(Long id) {
        webClient.build()
                .delete()
                .uri("http://movie-service/service/cinema/delete/" + id)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                .bodyToMono(Map.class)
                .block();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findAll() {
        System.out.println("=======Cinema=======");

        cinemaMapper.map(webClient.build()
                .get()
                .uri("http://movie-service/service/cinema/all")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .bodyToMono(LinkedHashSet.class)
                .block())
                .forEach(System.out::println);
    }
}
