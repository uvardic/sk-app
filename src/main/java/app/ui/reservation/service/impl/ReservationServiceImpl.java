package app.ui.reservation.service.impl;

import app.dto.reservation.Reservation;
import app.dto.reservation.ReservedSeat;
import app.mapper.reservation.ReservationMapper;
import app.token.service.TokenService;
import app.ui.reservation.service.ReservationService;
import app.ui.stage.StageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final StageListener stageListener;

    private final ReservationMapper reservationMapper;

    private final WebClient.Builder webClient;

    private final TokenService tokenService;

    private final Resource indexParent;

    public ReservationServiceImpl(StageListener stageListener, ReservationMapper reservationMapper,
                                  WebClient.Builder webClient, TokenService tokenService,
                                  @Value("classpath:/ui/fxml/index.fxml") Resource indexParent) {
        this.stageListener = stageListener;
        this.reservationMapper = reservationMapper;
        this.webClient = webClient;
        this.tokenService = tokenService;
        this.indexParent = indexParent;
    }

    @Override
    public boolean reserve(Long projectionId, Float totalPrice, List<ReservedSeat> selectedSeats) {
        Reservation reservation = new Reservation();
        reservation.setProjectionId(projectionId);
        reservation.setUserId(Long.valueOf((Integer) tokenService.parseToken(tokenService.readToken()).get("id")));
        reservation.setReservedSeats(selectedSeats);
        reservation.setTotalPrice(totalPrice);

        Reservation response = webClient.build()
                .post()
                .uri("http://reservation-service/service/reservation/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .body(BodyInserters.fromObject(reservation))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> Mono.empty())
                .bodyToMono(Reservation.class)
                .block();

        return response != null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Reservation> findReservationsByProjectionId(Long projectionId) {
        List<Reservation> reservations = reservationMapper.map(webClient.build()
                .get()
                .uri("http://reservation-service/service/reservation/all")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(tokenService.readToken()))
                .retrieve()
                .bodyToMono(LinkedHashSet.class)
                .block());

        return reservations.stream()
                .filter(reservation -> reservation.getProjectionId().equals(projectionId))
                .collect(Collectors.toList());
    }

    @Override
    public void loadIndexParent() {
        stageListener.setScene(indexParent);
    }
}
