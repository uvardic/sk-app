package app.ui.admin.service.impl;

import app.dto.movie.Cinema;
import app.dto.movie.Movie;
import app.dto.movie.Projection;
import app.mapper.movie.ProjectionMapper;
import app.token.service.TokenService;
import app.ui.admin.service.ProjectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;
import java.util.Map;

@Service
public class ProjectionServiceImpl implements ProjectionService {

    private final WebClient.Builder webClient;

    private final ProjectionMapper projectionMapper;

    private final TokenService tokenService;

    public ProjectionServiceImpl(WebClient.Builder webClient, ProjectionMapper projectionMapper,
                                 TokenService tokenService) {
        this.webClient = webClient;
        this.projectionMapper = projectionMapper;
        this.tokenService = tokenService;
    }

    @Override
    public boolean save(Projection projection) {
        Projection response = webClient.build()
                .post()
                .uri("http://movie-service/service/projection/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .body(BodyInserters.fromObject(projection))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> Mono.empty())
                .bodyToMono(Projection.class)
                .block();

        return response != null;
    }

    @Override
    public Movie findMovie(Long id) {
        return webClient.build()
                .get()
                .uri("http://movie-service/service/movie/" + id)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .bodyToMono(Movie.class)
                .block();
    }

    @Override
    public Cinema findCinema(Long id) {
        return webClient.build()
                .get()
                .uri("http://movie-service/service/cinema/" + id)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .bodyToMono(Cinema.class)
                .block();
    }

    @Override
    public void delete(Long id) {
        webClient.build()
                .delete()
                .uri("http://movie-service/service/projection/delete/" + id)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                .bodyToMono(Map.class)
                .block();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findAll() {
        System.out.println("=======Projection=======");

        projectionMapper.map(webClient.build()
                .get()
                .uri("http://movie-service/service/projection/all")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .bodyToMono(LinkedHashSet.class)
                .block())
                .forEach(System.out::println);
    }
}
