package app.dto.movie;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Movie {

    private Long id;

    private String name;

    private String genre;

    private String description;

    private Integer length;

    private List<String> actors;

}
