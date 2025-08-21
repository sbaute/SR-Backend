package com.serviciosRosario.ServiciosRosario.service.user;

import com.serviciosRosario.ServiciosRosario.entity.user.DTO.PasswordChangeDto;
import com.serviciosRosario.ServiciosRosario.entity.user.DTO.UserRegisterRequest;
import com.serviciosRosario.ServiciosRosario.entity.user.DTO.UserRequestDto;
import com.serviciosRosario.ServiciosRosario.entity.user.DTO.UserResponseDto;
import com.serviciosRosario.ServiciosRosario.entity.user.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService {
    List<User> getAll();
    User getById(Integer id);
    User getProfile();
    User registerUser(UserRegisterRequest userDto);
    User update(Integer id, Map<String, Object> updates);
    void delete(Integer id);
    boolean existsById(Integer id);
    Optional<User> findOneByUsername(String username);
    void changePassword(PasswordChangeDto passwordChangeDto, Integer id);
    Boolean checkSuperAdminAuthenticatedPermission();
    UserResponseDto transformUserToUserResponseDto(User user);
}
