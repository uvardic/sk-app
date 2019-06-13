package app.token.service;

import io.jsonwebtoken.Claims;

public interface TokenService {

    Claims parseToken(String jwt);

    void writeToken(String jwt);

    void deleteToken();

    String readToken();

}