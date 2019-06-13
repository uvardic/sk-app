package app.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class BanHistory {

    private Long adminId;

    private Date date;

}
