package app.mapper.movie;

import app.dto.movie.Projection;
import app.dto.movie.ProjectionStatus;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProjectionMapper {

    private final MovieMapper movieMapper;

    private final CinemaMapper cinemaMapper;

    public ProjectionMapper(MovieMapper movieMapper, CinemaMapper cinemaMapper) {
        this.movieMapper = movieMapper;
        this.cinemaMapper = cinemaMapper;
    }

    public List<Projection> map(LinkedHashSet<LinkedHashMap<Object, Object>> restResponse) {
        if (restResponse == null)
            throw new NullPointerException();

        return restResponse.stream()
                .map(this::mapProjection)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private Projection mapProjection(LinkedHashMap<Object, Object> projectionMap) {
        Projection projection = new Projection();
        projection.setId(Long.valueOf((Integer) projectionMap.get("id")));
        projection.setMovie(movieMapper.mapMovie((LinkedHashMap<Object, Object>) projectionMap.get("movie")));
        projection.setCinema(cinemaMapper.mapCinema((LinkedHashMap<Object, Object>) projectionMap.get("cinema")));
        projection.setStartDate((String) projectionMap.get("startDate"));
        projection.setTicketPrice(((Double) projectionMap.get("ticketPrice")).floatValue());
        projection.setStatus(matchStatusNameWithString((String) projectionMap.get("status")));
        projection.setNumberOfReservations((Integer) projectionMap.get("numberOfReservations"));
        return projection;
    }

//    private Date parseDate(String dateString) {
//        try {
//            return new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(dateString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        throw new RuntimeException("Date parsing error");
//    }

    public ProjectionStatus matchStatusNameWithString(String name) {
        return Arrays.stream(ProjectionStatus.values())
                .filter(statusName -> statusName.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Projection status not found for name: " + name));
    }

}
