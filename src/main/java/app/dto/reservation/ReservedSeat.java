package app.dto.reservation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservedSeat {

    private Integer reservedRow;

    private Integer reservedColumn;

}
