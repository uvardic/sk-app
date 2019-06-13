package app.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class User {

    private Long id;

    private UserRole userRole;

    private UserStatus userStatus;

    private String username;

    private String password;

    private String email;

    private Integer numberOfReservations;

    private String firstName;

    private String lastName;

    private boolean banned;

    private List<BanHistory> banHistory;

}
