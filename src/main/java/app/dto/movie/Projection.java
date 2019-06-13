package app.dto.movie;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class Projection {

    private Long id;

    private Movie movie;

    private Cinema cinema;

    private Date startDate;

    private Float ticketPrice;

    private ProjectionStatus status;

    private Integer numberOfReservations;

    public String getStartDate() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(startDate);
    }

    public Date getStartDateAsDateObject() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        try {
            this.startDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
