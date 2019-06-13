package app.ui.reservation.service;

import app.dto.reservation.Reservation;
import app.dto.reservation.ReservedSeat;

import java.util.List;

public interface ReservationService {

    boolean reserve(Long projectionId, Float totalPrice, List<ReservedSeat> selectedSeats);

    List<Reservation> findReservationsByProjectionId(Long projectionId);

    void loadIndexParent();

}
