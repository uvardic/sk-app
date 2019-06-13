package app.ui.reservation.controller;

import app.dto.movie.Projection;
import app.dto.reservation.Reservation;
import app.dto.reservation.ReservedSeat;
import app.ui.reservation.service.ReservationService;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    private Projection projection;

    public void setProjection(Projection projection) {
        this.projection = projection;
        initialize();
    }

    @FXML
    private VBox container;

    private Seats seats;

    private void initialize() {
        seats = new Seats();
        initializeSeatUi();
    }

    private void initializeSeatUi() {
        container.getChildren().clear();

        List<Reservation> reservations = service.findReservationsByProjectionId(projection.getId());

        reservations.forEach(reservation -> reservation.getReservedSeats()
                .forEach(reservedSeat -> markSeatAsTaken(reservedSeat.getReservedRow(), reservedSeat.getReservedColumn())));

        IntStream.range(0, projection.getCinema().getNumberOfRows())
                .forEach(this::createRow);

    }

    private void markSeatAsTaken(int row, int column) {
        Seat seat = seats.getSeat(row, column);

        seat.setTaken(true);
        seat.setImagePath("/ui/img/seatTaken.png");
    }

    private void createRow(int row) {
        HBox rowBox = new HBox();
        rowBox.setSpacing(5);

        IntStream.range(0, projection.getCinema().getNumberOfSeatsPerRow())
                .forEach(column -> createColumn(row, column, rowBox));

        container.getChildren().add(rowBox);
    }

    private void createColumn(int row, int column, HBox rowBox) {
        Seat seat = seats.getSeat(row, column);

        Image seatImage = new Image(getClass().getResourceAsStream(seat.getImagePath()));
        ImageView imageView = new ImageView(seatImage);

        imageView.setOnMouseClicked(event -> seatMouseEvent(seat));

        rowBox.getChildren().add(imageView);
    }

    private void seatMouseEvent(Seat seat) {
        if (seat.isTaken())
            return;

        if (seat.isSelected()) {
            seat.setSelected(false);
            seat.setImagePath("/ui/img/seat.png");
        } else {
            seat.setSelected(true);
            seat.setImagePath("/ui/img/seatSelected.png");
        }

        initializeSeatUi();
    }

    @FXML
    private void backAction() {
        service.loadIndexParent();
    }

    @FXML
    private void reserveAction() {
        List<Seat> selectedSeats = seats.getSelectedSeats();

        if (selectedSeats.isEmpty())
            return;

//        List<ReservedSeat> reservedSeats = selectedSeats.stream()
//                .map(this::mapSeat)
//                .collect(Collectors.toList());

//        Float totalPrice = projection.getTicketPrice() * reservedSeats.size();
//
//        service.reserve(projection.getId(), 0f, reservedSeats);
    }

//    private ReservedSeat mapSeat(Seat seat) {
//
//    }

    @Data
    private class Seats {

        private Seat[][] seats;

        private Seats() {
            int rows = projection.getCinema().getNumberOfRows();
            int columns = projection.getCinema().getNumberOfSeatsPerRow();

            this.seats = new Seat[rows][columns];

            IntStream.range(0, rows)
                    .forEach(row -> IntStream.range(0, columns)
                            .forEach(column -> seats[row][column] = new Seat(row, column)));
        }

        private Seat getSeat(int row, int column) {
            return seats[row][column];
        }

        private List<Seat> getSelectedSeats() {
            int rows = projection.getCinema().getNumberOfRows();
            int columns = projection.getCinema().getNumberOfSeatsPerRow();

            List<Seat> selectedSeats = new ArrayList<>();

            IntStream.range(0, rows)
                    .forEach(row -> IntStream.range(0, columns)
                            .filter(column -> seats[row][column].isSelected())
                            .forEach(column -> selectedSeats.add(seats[row][column])));

            return selectedSeats;
        }

    }

    @Data
    @NoArgsConstructor
    public class Seat {

        private Integer row;

        private Integer column;

        private String imagePath;

        private boolean taken;

        private boolean selected;

        private Seat(Integer row, Integer column) {
            this.row = row;
            this.column = column;
            this.imagePath = "/ui/img/seat.png";
        }

    }

}
