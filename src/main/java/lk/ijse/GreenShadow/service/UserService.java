package lk.ijse.GreenShadow.service;

import jakarta.validation.Valid;
import lk.ijse.GreenShadow.dto.UserDTO;
import lk.ijse.GreenShadow.response.UserResponse;

import java.util.List;

public interface UserService {
    void addUser(@Valid UserDTO userDTO);
    void removeUser(String email);
    void updateUser(String email, UserDTO userDTO);
    UserResponse getUser(String email);
    List<UserDTO> getAllUsers();
}
