package app.ui.login.service;

import app.dto.user.RoleName;

public interface LoginService {

    boolean login(String username, String password);

    RoleName getLoggedRoleName();

    void loadRegisterParent();

    void loadIndexParent();

    void loadAdminParent();

}
