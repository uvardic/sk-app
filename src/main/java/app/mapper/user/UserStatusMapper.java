package app.mapper.user;

import app.dto.user.StatusName;
import app.dto.user.UserStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserStatusMapper {

    public List<UserStatus> map(LinkedHashSet<LinkedHashMap<Object, Object>> restResponse) {
        if (restResponse == null)
            throw new NullPointerException();

        return restResponse.stream()
                .map(this::mapUserStatus)
                .collect(Collectors.toList());
    }

    public UserStatus mapUserStatus(LinkedHashMap<Object, Object> userStatusMap) {
        UserStatus userStatus = new UserStatus();
        userStatus.setId(Long.valueOf((Integer) userStatusMap.get("id")));
        userStatus.setName(matchStatusNameWithString((String) userStatusMap.get("name")));
        userStatus.setRequiredPoints((Integer) userStatusMap.get("requiredPoints"));
        userStatus.setMaximumPoints((Integer) userStatusMap.get("maximumPoints"));
        userStatus.setDiscount(((Double) userStatusMap.get("discount")).floatValue());
        return userStatus;
    }

    public StatusName matchStatusNameWithString(String name) {
        return Arrays.stream(StatusName.values())
                .filter(roleName -> roleName.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Role name not found for name: " + name));

    }

}
