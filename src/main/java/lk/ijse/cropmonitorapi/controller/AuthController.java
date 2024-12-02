package lk.ijse.cropmonitorapi.controller;

import lk.ijse.cropmonitorapi.exception.DataPersistFailedException;
import lk.ijse.cropmonitorapi.exception.InvalidCredentialsException;
import lk.ijse.cropmonitorapi.exception.UserAlreadyExistsException;
import lk.ijse.cropmonitorapi.exception.UserNotFoundException;
import lk.ijse.cropmonitorapi.jwtmodels.AuthResponse;
import lk.ijse.cropmonitorapi.jwtmodels.RegisterUser;
import lk.ijse.cropmonitorapi.jwtmodels.UserLogin;
import lk.ijse.cropmonitorapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationService authService;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AuthResponse> registerUser(
            @RequestPart("email") String email,
            @RequestPart("password") String password
    ) {
        try{
            RegisterUser user = new RegisterUser();
            user.setEmail(email);
            user.setPassword(encoder.encode(password));
            return ResponseEntity.ok(authService.registerUser(user));
        } catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // this returns a 409 status code when the user already exists
        } catch (UserNotFoundException e) {
          return ResponseEntity.notFound().build(); // this returns a 404 status code when the staff is not found
        } catch (DataPersistFailedException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AuthResponse> login(
            @RequestPart("email") String email,
            @RequestPart("password") String password
    ){
        try {
            AuthResponse response = authService.login(new UserLogin(email, password));
            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // this returns a 401 status code when the credentials are invalid
        }  catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<AuthResponse> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }


}
