package app.ui.admin.service;

import app.dto.movie.Movie;

public interface MovieService {

    boolean save(Movie movie);

    void delete(Long id);

    void findAll();

}
