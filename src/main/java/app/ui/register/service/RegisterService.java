package app.ui.register.service;

public interface RegisterService {

    boolean register(String username, String password, String email);

    void loadLoginParent();

}
