package app.mapper.movie;

import app.dto.movie.Cinema;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CinemaMapper {

    public List<Cinema> map(LinkedHashSet<LinkedHashMap<Object, Object>> restResponse) {
        if (restResponse == null)
            throw new NullPointerException();

        return restResponse.stream()
                .map(this::mapCinema)
                .collect(Collectors.toList());
    }

    public Cinema mapCinema(LinkedHashMap<Object, Object> cinemaMap) {
        Cinema cinema = new Cinema();
        cinema.setId(Long.valueOf((Integer) cinemaMap.get("id")));
        cinema.setCinemaNumber((Integer) cinemaMap.get("cinemaNumber"));
        cinema.setNumberOfRows((Integer) cinemaMap.get("numberOfRows"));
        cinema.setNumberOfSeatsPerRow((Integer) cinemaMap.get("numberOfSeatsPerRow"));
        return cinema;
    }
}
