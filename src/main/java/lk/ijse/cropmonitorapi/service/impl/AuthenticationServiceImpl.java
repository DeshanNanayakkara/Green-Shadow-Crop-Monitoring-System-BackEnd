package lk.ijse.cropmonitorapi.service.impl;


import lk.ijse.cropmonitorapi.entity.Role;
import lk.ijse.cropmonitorapi.entity.User;
import lk.ijse.cropmonitorapi.exception.InvalidCredentialsException;
import lk.ijse.cropmonitorapi.exception.UserAlreadyExistsException;
import lk.ijse.cropmonitorapi.exception.UserNotFoundException;
import lk.ijse.cropmonitorapi.jwtmodels.AuthResponse;
import lk.ijse.cropmonitorapi.jwtmodels.RegisterUser;
import lk.ijse.cropmonitorapi.jwtmodels.UserLogin;
import lk.ijse.cropmonitorapi.repository.StaffRepository;
import lk.ijse.cropmonitorapi.repository.UserRepository;
import lk.ijse.cropmonitorapi.service.AuthenticationService;
import lk.ijse.cropmonitorapi.service.JwtService;
import lk.ijse.cropmonitorapi.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final JwtService jwtService;
    private final Mapping mapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse registerUser(RegisterUser user) {
        if (!userRepository.existsUserByEmail(user.getEmail())) {
            Optional<Role> optionalRole = staffRepository.findRoleIfEmailExists(user.getEmail());

            if (optionalRole.isPresent() && optionalRole.get() == Role.MANAGER) {
                User register = new User(
                        user.getEmail(),
                        user.getPassword(),
                        optionalRole.get()
                );
                User saved = userRepository.save(register);
                String generatedToken = jwtService.generateToken(saved);
                return AuthResponse.builder().token(generatedToken).build();
            } else {
                throw new UserNotFoundException("Cannot find manager assigned with this email");
            }
        }
        throw new UserAlreadyExistsException("User already exists");
    }

    @Override
    public AuthResponse login(UserLogin login) throws InvalidCredentialsException {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
            var userByEmail = userRepository.findByEmail(login.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            String generatedToken = jwtService.generateToken(userByEmail);
            return AuthResponse.builder().token(generatedToken).build();
        } catch (UserNotFoundException | AuthenticationException e){
            throw new InvalidCredentialsException("Invalid credential");
        }
    }

    @Override
    public AuthResponse refreshToken(String accessToken) {
        var username = jwtService.extractUserEmail(accessToken);
        var userEntity =
                userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        var refreshToken = jwtService.refreshToken(userEntity);
        return AuthResponse.builder().token(refreshToken).build();
    }
}
