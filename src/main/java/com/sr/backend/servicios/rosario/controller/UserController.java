package com.serviciosRosario.ServiciosRosario.controller;

import com.serviciosRosario.ServiciosRosario.entity.user.DTO.*;
import com.serviciosRosario.ServiciosRosario.entity.user.User;
import com.serviciosRosario.ServiciosRosario.payload.MessageClient;
import com.serviciosRosario.ServiciosRosario.payload.ResponseMessage;
import com.serviciosRosario.ServiciosRosario.security.service.AuthService;
import com.serviciosRosario.ServiciosRosario.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    @Autowired
    private AuthService authService;

    @Autowired
    private IUserService userService;


    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<User> users = userService.getAll();
        List<UserResponseDto> userResponseDto = users.stream()
                .map(user -> userService.transformUserToUserResponseDto(user))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(userService.transformUserToUserResponseDto(user), HttpStatus.OK);
    }

    @GetMapping("/getProfile")
    public ResponseEntity<UserResponseDto> getProfile() {
        User user = userService.getProfile();
        return new ResponseEntity<>(userService.transformUserToUserResponseDto(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        UserRegisterResponse userRegisterResponse = authService.registerOneUser(userRegisterRequest);
          return new ResponseEntity<>(ResponseMessage.builder()
                .message(MessageClient.USER_REGISTER_SUCCESS)
                .object(userRegisterResponse)
                .build(),
                HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseMessage> update(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        try {

            User updatedUser = userService.update(id, updates);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .message(MessageClient.USER_UPDATED_SUCCESS)
                    .object(userService.transformUserToUserResponseDto(updatedUser))
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Database error: " + exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Error: " + ex.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable Integer id){
        try{
            userService.delete(id);
            return new ResponseEntity<>(ResponseMessage
                    .builder()
                    .message(MessageClient.USER_DELETE_SUCCESS)
                    .object("")
                    .build(), HttpStatus.NO_CONTENT);
        } catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Database error: " + exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Error: " + ex.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.BAD_REQUEST);
        }


    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<ResponseMessage> changePassword(@PathVariable Integer id, @RequestBody PasswordChangeDto passwordChangeDto){
       try {
           userService.changePassword(passwordChangeDto, id);
           return new ResponseEntity<>(ResponseMessage.builder()
                   .message(MessageClient.PASSWORD_UPDATED_SUCCESS)
                   .object("")
                   .build(),
                   HttpStatus.OK);
       }catch (DataAccessException exDT) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .message("Database error: " + exDT.getMessage())
                    .object(null)
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
           return new ResponseEntity<>(ResponseMessage.builder()
                   .message("Error: " + ex.getMessage())
                   .object(null)
                   .build(),
                   HttpStatus.BAD_REQUEST);
       }

    }


}
