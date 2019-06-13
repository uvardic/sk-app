package app.dto.reservation;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Reservation {

    private Long id;

    private Long projectionId;

    private Long userId;

    private Float totalPrice;

    private List<ReservedSeat> reservedSeats;

    private transient String jwt;

}
