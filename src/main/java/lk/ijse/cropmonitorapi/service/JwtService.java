package lk.ijse.cropmonitorapi.service;


import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserEmail(String token);
    String generateToken(UserDetails userDetails);
    String refreshToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}
