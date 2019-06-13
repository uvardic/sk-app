package app.token.service.impl;

import app.token.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Scanner;

@Service
public class TokenServiceImpl implements TokenService {

    private final String jwtSecret;

    private final String jwtFilePath;

    public TokenServiceImpl(@Value("${oauth.jwt.secret}") String jwtSecret,
                            @Value("${oauth.jwt.file.path}") String jwtFilePath) {
        this.jwtSecret = jwtSecret;
        this.jwtFilePath = jwtFilePath;
    }

    @Override
    public Claims parseToken(String jwt) {
        Claims claims;

        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }

        return claims;
    }

    @Override
    public void writeToken(String jwt) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(jwtFilePath)))) {
            writer.println(jwt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void deleteToken() {
        new File(jwtFilePath).delete();
    }

    @Override
    public String readToken() {
        try (final Scanner scanner = new Scanner(new File(jwtFilePath))) {
            return scanner.useDelimiter("\\Z").next();
        } catch (final FileNotFoundException ex) {
            ex.printStackTrace();
        }

        throw new RuntimeException("Jwt file not found");
    }
}
