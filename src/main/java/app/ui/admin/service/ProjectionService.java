package app.ui.admin.service;

import app.dto.movie.Cinema;
import app.dto.movie.Movie;
import app.dto.movie.Projection;

public interface ProjectionService {

    boolean save(Projection projection);

    Movie findMovie(Long id);

    Cinema findCinema(Long id);

    void delete(Long id);

    void findAll();

}
