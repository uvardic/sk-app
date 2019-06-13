package app.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRole {

    private Long id;

    private RoleName name;

    private String description;

}
