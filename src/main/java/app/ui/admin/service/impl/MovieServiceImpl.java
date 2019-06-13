package app.ui.admin.service.impl;

import app.dto.movie.Movie;
import app.mapper.movie.MovieMapper;
import app.token.service.TokenService;
import app.ui.admin.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {

    private final WebClient.Builder webClient;

    private final MovieMapper movieMapper;

    private final TokenService tokenService;

    public MovieServiceImpl(WebClient.Builder webClient, MovieMapper movieMapper,
                            TokenService tokenService) {
        this.webClient = webClient;
        this.movieMapper = movieMapper;
        this.tokenService = tokenService;
    }

    @Override
    public boolean save(Movie movie) {
        Movie response = webClient.build()
                .post()
                .uri("http://movie-service/service/movie/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .body(BodyInserters.fromObject(movie))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> Mono.empty())
                .bodyToMono(Movie.class)
                .block();

        return response != null;
    }

    @Override
    public void delete(Long id) {
        webClient.build()
                .delete()
                .uri("http://movie-service/service/movie/delete/" + id)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                .bodyToMono(Map.class)
                .block();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findAll() {
        System.out.println("=======Movie=======");

        movieMapper.map(webClient.build()
                .get()
                .uri("http://movie-service/service/movie/all")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .bodyToMono(LinkedHashSet.class)
                .block())
                .forEach(System.out::println);
    }
}
