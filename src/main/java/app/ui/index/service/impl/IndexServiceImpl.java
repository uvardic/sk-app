package app.ui.index.service.impl;

import app.dto.movie.Cinema;
import app.dto.movie.Movie;
import app.dto.movie.Projection;
import app.ui.index.service.IndexService;
import app.mapper.movie.CinemaMapper;
import app.mapper.movie.MovieMapper;
import app.mapper.movie.ProjectionMapper;
import app.token.service.TokenService;
import app.ui.reservation.controller.ReservationController;
import app.ui.stage.StageListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    private final WebClient.Builder webClient;

    private final MovieMapper movieMapper;

    private final CinemaMapper cinemaMapper;

    private final ProjectionMapper projectionMapper;

    private final TokenService tokenService;

    private final StageListener stageListener;

    private final Resource reservationParent;

    public IndexServiceImpl(WebClient.Builder webClient, MovieMapper movieMapper, CinemaMapper cinemaMapper,
                            ProjectionMapper projectionMapper, TokenService tokenService, StageListener stageListener,
                            @Value("classpath:/ui/fxml/reservation.fxml") Resource reservationParent) {
        this.webClient = webClient;
        this.movieMapper = movieMapper;
        this.cinemaMapper = cinemaMapper;
        this.projectionMapper = projectionMapper;
        this.tokenService = tokenService;
        this.stageListener = stageListener;
        this.reservationParent = reservationParent;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Movie> findAllMovies() {
        return movieMapper.map(webClient.build()
                .get()
                .uri("http://movie-service/service/movie/all")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .bodyToMono(LinkedHashSet.class)
                .block());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Cinema> findAllCinemas() {
        return cinemaMapper.map(webClient.build()
                .get()
                .uri("http://movie-service/service/cinema/all")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .bodyToMono(LinkedHashSet.class)
                .block());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Projection> findProjections(Long movieId, Long cinemaId, String projectionDate) {
        return projectionMapper.map(webClient.build()
                .get()
                .uri(String.format("http://movie-service/service/projection/all/%d/%d/%s", movieId, cinemaId, projectionDate))
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .bodyToMono(LinkedHashSet.class)
                .block());
    }

    @Override
    public void loadReservationParent(Projection projection) {
        try {
            FXMLLoader loader = stageListener.getFXMLLoaderFor(reservationParent);
            stageListener.getStage().setScene(new Scene(loader.load()));
            ReservationController reservationController = loader.getController();
            reservationController.setProjection(projection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
