package app.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserStatus {

    private Long id;

    private StatusName name;

    private Integer requiredPoints;

    private Integer maximumPoints;

    private Float discount;

}
