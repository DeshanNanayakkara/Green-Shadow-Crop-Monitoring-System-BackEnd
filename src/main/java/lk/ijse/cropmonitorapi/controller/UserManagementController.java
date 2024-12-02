package lk.ijse.cropmonitorapi.controller;


import jakarta.validation.Valid;
import lk.ijse.cropmonitorapi.dto.UserDTO;
import lk.ijse.cropmonitorapi.exception.DataPersistFailedException;
import lk.ijse.cropmonitorapi.exception.UserNotFoundException;
import lk.ijse.cropmonitorapi.response.UserResponse;
import lk.ijse.cropmonitorapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserManagementController {
    private final UserService userService;
    static Logger logger = LoggerFactory.getLogger(UserManagementController.class);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUser(@Valid @RequestBody UserDTO userDTO) {
        if (userDTO == null) {
            logger.warn("Invalid request : User object is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try{
                userService.addUser(userDTO);
                logger.info("User with User email: {} saved successfully", userDTO.getEmail());
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (DataPersistFailedException e) {
                logger.error("Failed to save user: {}", userDTO, e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                logger.error("Internal server error while saving user: {}", userDTO, e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping(value = "/{userEmail}")
    public ResponseEntity<Void> removeUser(@PathVariable("userEmail") String email){
        try{
            if (email == null || email.isEmpty()){
                logger.warn("Invalid request : User email is null or empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                userService.removeUser(email);
                logger.info("User with User email: {} removed successfully", email);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (UserNotFoundException e) {
            logger.error("Failed to remove user with email: {}", email, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Internal server error while removing user with email: {}", email, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{userEmail}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@PathVariable("userEmail") String email, @RequestBody UserDTO userDTO){
        try{
            if (email == null || email.isEmpty() || userDTO == null){
                logger.warn("Invalid request : User email or User object is null or empty");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                userService.updateUser(email, userDTO);
                logger.info("User with User email: {} updated successfully", email);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (UserNotFoundException e) {
            logger.error("Failed to update user with email: {}", email, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Internal server error while updating user with email: {}", email, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getUser(@PathVariable("userEmail") String email) {
        return userService.getUser(email);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}
