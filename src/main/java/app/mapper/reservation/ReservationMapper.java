package app.mapper.reservation;

import app.dto.reservation.Reservation;
import app.dto.reservation.ReservedSeat;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservationMapper {

    public List<Reservation> map(LinkedHashSet<LinkedHashMap<Object, Object>> restResponse) {
        if (restResponse == null)
            throw new NullPointerException();

        return restResponse.stream()
                .map(this::mapReservation)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private Reservation mapReservation(LinkedHashMap<Object, Object> reservationMap) {
        Reservation reservation = new Reservation();
        reservation.setId(Long.valueOf((Integer) reservationMap.get("id")));
        reservation.setProjectionId(Long.valueOf((Integer) reservationMap.get("projectionId")));
        reservation.setUserId(Long.valueOf((Integer) reservationMap.get("userId")));
        reservation.setTotalPrice(((Double) reservationMap.get("totalPrice")).floatValue());
        reservation.setReservedSeats(mapReservedSeats((List<LinkedHashMap<Object, Object>>) reservationMap.get("reservedSeats")));
        return reservation;
    }

    private List<ReservedSeat> mapReservedSeats(List<LinkedHashMap<Object, Object>> restResponse) {
        if (restResponse == null)
            return new ArrayList<>();

        return restResponse.stream()
                .map(this::mapReservedSeat)
                .collect(Collectors.toList());
    }

    private ReservedSeat mapReservedSeat(LinkedHashMap<Object, Object> reservedSeatMap) {
        ReservedSeat reservedSeat = new ReservedSeat();
        reservedSeat.setReservedRow((Integer) reservedSeatMap.get("reservedRow"));
        reservedSeat.setReservedColumn((Integer) reservedSeatMap.get("reservedColumn"));
        return reservedSeat;
    }

}