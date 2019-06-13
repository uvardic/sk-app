package app.dto.movie;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cinema {

    private Long id;

    private Integer cinemaNumber;

    private Integer numberOfRows;

    private Integer numberOfSeatsPerRow;

}
