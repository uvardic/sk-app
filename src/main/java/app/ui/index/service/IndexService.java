package app.ui.index.service;

import app.dto.movie.Cinema;
import app.dto.movie.Movie;
import app.dto.movie.Projection;

import java.util.List;

public interface IndexService {

    List<Movie> findAllMovies();

    List<Cinema> findAllCinemas();

    List<Projection> findProjections(Long movieId, Long cinemaId, String projectionDate);

    void loadReservationParent(Projection projection);

}
