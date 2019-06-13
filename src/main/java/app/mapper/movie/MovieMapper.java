package app.mapper.movie;

import app.dto.movie.Movie;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    public List<Movie> map(LinkedHashSet<LinkedHashMap<Object, Object>> restResponse) {
        if (restResponse == null)
            throw new NullPointerException();

        return restResponse.stream()
                .map(this::mapMovie)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public Movie mapMovie(LinkedHashMap<Object, Object> movieMap) {
        Movie movie = new Movie();
        movie.setId(Long.valueOf((Integer) movieMap.get("id")));
        movie.setName((String) movieMap.get("name"));
        movie.setGenre((String) movieMap.get("genre"));
        movie.setDescription((String) movieMap.get("description"));
        movie.setLength((Integer) movieMap.get("length"));
        movie.setActors((List<String>) movieMap.get("actors"));
        return movie;
    }

}
