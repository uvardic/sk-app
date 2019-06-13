package app.mapper.user;

import app.dto.user.RoleName;
import app.dto.user.UserRole;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRoleMapper {

    public List<UserRole> map(LinkedHashSet<LinkedHashMap<Object, Object>> restResponse) {
        if (restResponse == null)
            throw new NullPointerException();

        return restResponse.stream()
                .map(this::mapUserRole)
                .collect(Collectors.toList());
    }

    public UserRole mapUserRole(LinkedHashMap<Object, Object> userRoleMap) {
        UserRole userRole = new UserRole();
        userRole.setId(Long.valueOf((Integer) userRoleMap.get("id")));
        userRole.setName(matchRoleNameWithString((String) userRoleMap.get("name")));
        userRole.setDescription((String) userRoleMap.get("description"));
        return userRole;
    }

    public RoleName matchRoleNameWithString(String name) {
        return Arrays.stream(RoleName.values())
                .filter(roleName -> roleName.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Role name not found for name: " + name));
    }

}
