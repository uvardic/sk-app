package app.mapper.user;

import app.dto.user.BanHistory;
import app.dto.user.User;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final UserRoleMapper userRoleMapper;

    private final UserStatusMapper userStatusMapper;

    public UserMapper(UserRoleMapper userRoleMapper, UserStatusMapper userStatusMapper) {
        this.userRoleMapper = userRoleMapper;
        this.userStatusMapper = userStatusMapper;
    }

    public List<User> map(LinkedHashSet<LinkedHashMap<Object, Object>> restResponse) {
        if (restResponse == null)
            throw new NullPointerException();

        return restResponse.stream()
                .map(this::mapUser)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private User mapUser(LinkedHashMap<Object, Object> userMap) {
        User user = new User();
        user.setId(Long.valueOf((Integer) userMap.get("id")));
        user.setUserRole(userRoleMapper.mapUserRole((LinkedHashMap<Object, Object>) userMap.get("userRole")));
        user.setUserStatus(userStatusMapper.mapUserStatus((LinkedHashMap<Object, Object>) userMap.get("userStatus")));
        user.setUsername((String) userMap.get("username"));
        user.setPassword((String) userMap.get("password"));
        user.setEmail((String) userMap.get("email"));
        user.setNumberOfReservations((Integer) userMap.get("numberOfReservations"));
        user.setFirstName((String) userMap.get("firstName"));
        user.setLastName((String) userMap.get("lastName"));
        user.setBanned((boolean) userMap.get("banned"));
        user.setBanHistory(mapBanHistories((List<LinkedHashMap<Object, Object>>) userMap.get("banHistory")));
        return user;
    }

    private List<BanHistory> mapBanHistories(List<LinkedHashMap<Object, Object>> restResponse) {
        if (restResponse == null)
            return new ArrayList<>();

        return restResponse.stream()
                .map(this::mapBanHistory)
                .collect(Collectors.toList());
    }

    private BanHistory mapBanHistory(LinkedHashMap<Object, Object> banHistoryMap) {
        BanHistory banHistory = new BanHistory();
        banHistory.setAdminId(Long.valueOf((Integer) banHistoryMap.get("adminId")));
        banHistory.setDate(parseDate((String) banHistoryMap.get("date")));
        return banHistory;
    }

    private Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Date parsing error");
    }
}
