package app.ui.admin.service;

import app.dto.user.User;
import app.dto.user.UserRole;
import app.dto.user.UserStatus;

public interface UserService {

    boolean save(User user);

    UserRole findUserRole(Long id);

    UserStatus findUserStatus(Long id);

    boolean delete(Long id);

    void ban(Long id);

    void findAll();

}
