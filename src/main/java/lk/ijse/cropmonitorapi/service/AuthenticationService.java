package lk.ijse.cropmonitorapi.service;


import lk.ijse.cropmonitorapi.exception.InvalidCredentialsException;
import lk.ijse.cropmonitorapi.jwtmodels.AuthResponse;
import lk.ijse.cropmonitorapi.jwtmodels.RegisterUser;
import lk.ijse.cropmonitorapi.jwtmodels.UserLogin;

public interface AuthenticationService {
    AuthResponse registerUser(RegisterUser user);
    AuthResponse login(UserLogin login) throws InvalidCredentialsException;
    AuthResponse refreshToken(String refreshToken);
}
