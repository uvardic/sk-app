package app.ui.admin.service;

import app.dto.user.UserStatus;

public interface UserStatusService {

    boolean save(UserStatus userStatus);

    void delete(Long id);

    void findAll();

}

