package app.ui.admin.service;

import app.dto.movie.Cinema;

public interface CinemaService {

    boolean save(Cinema cinema);

    void delete(Long id);

    void findAll();

}
