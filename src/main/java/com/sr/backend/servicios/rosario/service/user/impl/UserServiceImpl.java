package com.serviciosRosario.ServiciosRosario.service.user.impl;


import com.serviciosRosario.ServiciosRosario.entity.role.Role;
import com.serviciosRosario.ServiciosRosario.entity.user.DTO.PasswordChangeDto;
import com.serviciosRosario.ServiciosRosario.entity.user.DTO.UserRegisterRequest;
import com.serviciosRosario.ServiciosRosario.entity.user.DTO.UserResponseDto;
import com.serviciosRosario.ServiciosRosario.entity.user.User;
import com.serviciosRosario.ServiciosRosario.exceptions.auth.UnauthorizedException;
import com.serviciosRosario.ServiciosRosario.exceptions.auth.UserException;
import com.serviciosRosario.ServiciosRosario.exceptions.auth.InvalidPasswordException;
import com.serviciosRosario.ServiciosRosario.exceptions.errors.ErrorType;
import com.serviciosRosario.ServiciosRosario.repository.RoleRepository;
import com.serviciosRosario.ServiciosRosario.repository.UserRepository;
import com.serviciosRosario.ServiciosRosario.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException(ErrorType.USER_NOT_FOUND));
    }

    @Override
    public User getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(username).orElseThrow(() -> new UserException(ErrorType.USER_NOT_FOUND));
    }

    @Override
    public User registerUser(UserRegisterRequest userRegisterRequest) {

        Role role = roleRepository.findById(userRegisterRequest.getRole()).get();

        superArdminNotModifiedByAnotherSuperAdmin(role.getName());

        validatePassword(userRegisterRequest.getPassword(), userRegisterRequest.getRepeatPassword());

       User user = new User();
       user.setUsername(userRegisterRequest.getUsername());
       user.setName(userRegisterRequest.getName());
       user.setEmail(userRegisterRequest.getEmail());
       user.setPhone(userRegisterRequest.getPhone());
       user.setAddress(userRegisterRequest.getAddress());
       user.setRole(role);


        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User update(Integer id, Map<String, Object> updates) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(
                        ErrorType.USER_NOT_FOUND
                ));

        superArdminNotModifiedByAnotherSuperAdmin(user.getRole().getName());

        FieldUpdate(updates, user);

        return userRepository.save(user);
    }

    private void superArdminNotModifiedByAnotherSuperAdmin(String role) {
        if(!checkSuperAdminAuthenticatedPermission() && role.equals("SUPERADMIN")){
            throw new UnauthorizedException(ErrorType.USER_PERMISSION_DENIED_NOT_SUPERADMIN);
        }
    }


    @Override
    public void delete(Integer id) {
        User user = userRepository.findById(id).get();
        superArdminNotModifiedByAnotherSuperAdmin(user.getRole().getName());
        userRepository.delete(user);
    }

    @Override
    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void changePassword(PasswordChangeDto passwordChangeDto, Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(ErrorType.USER_NOT_FOUND));

        superArdminNotModifiedByAnotherSuperAdmin(user.getRole().getName());

        validatePassword(passwordChangeDto.getNewPassword(), passwordChangeDto.getNewPassword());
        user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));

        userRepository.save(user);
    }

    @Override
    public Boolean checkSuperAdminAuthenticatedPermission() {
        // Obtengo el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Obtengo los roles del usuario autenticado
         return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_SUPERADMIN"));
    }


    @Override
    public UserResponseDto transformUserToUserResponseDto(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .address(user.getAddress())
                .phone(user.getPhone())
                .role(user.getRole().getName())
                .build();
    }


    private void validatePassword(String password1, String password2) {

        if (!StringUtils.hasText(password1) || !StringUtils.hasText(password2)) {
            throw new InvalidPasswordException(ErrorType.PASSWORDS_EMPTY.getMessage());
        }

        if (!password1.equals(password2)) {
            throw new InvalidPasswordException(ErrorType.PASSWORDS_DO_NOT_MATCH.getMessage());
        }

        if(password1.length() < 8 || password2.length() < 8 ){
            throw new InvalidPasswordException(ErrorType.PASSWORD_TOO_SHORT.getMessage());
        }

        if(password1.length() > 12 || password2.length() > 12 ){
            throw new InvalidPasswordException(ErrorType.PASSWORD_TOO_LONG.getMessage());
        }

    }

    private void FieldUpdate(Map<String, Object> updates, User user) {
        updates.forEach((key, value) -> {
            switch (key) {
                case "username":
                    if (value instanceof String) {
                        userRepository.findByUsername(user.getUsername())
                                .ifPresent(existingUser -> {
                                    if (!existingUser.getId().equals(user.getId())) {
                                        throw new UserException(ErrorType.USER_ALREADY_EXISTS);
                                    }
                                });
                        user.setUsername((String) value);
                    } else {
                        throw new IllegalArgumentException(ErrorType.USERNAME_MUST_BE_STRING.getMessage());
                    }
                    break;

                case "name":
                    if (value instanceof String) {
                        user.setName((String) value);
                    } else {
                        throw new IllegalArgumentException(ErrorType.NAME_MUST_BE_STRING.getMessage());
                    }
                    break;

                case "email":
                    if (value instanceof String) {
                        user.setEmail((String) value);
                    } else {
                        throw new IllegalArgumentException(ErrorType.EMAIL_MUST_BE_STRING.getMessage());
                    }
                    break;

                case "address":
                    if (value instanceof String) {
                        user.setAddress((String) value);
                    } else {
                        throw new IllegalArgumentException(ErrorType.ADDRESS_MUST_BE_STRING.getMessage());
                    }
                    break;

                case "phone":
                    if (value instanceof String) {
                        user.setPhone((String) value);
                    } else {
                        throw new IllegalArgumentException(ErrorType.PHONE_MUST_BE_STRING.getMessage());
                    }
                    break;

                case "role":
                    if (value instanceof Integer) {
                        Integer roleId = (Integer) value;
                        Role role = roleRepository.findById(roleId)
                                .orElseThrow(() -> new IllegalArgumentException(ErrorType.ROLE_NOT_FOUND.getMessage()));
                        user.setRole(role);
                    } else {
                        throw new IllegalArgumentException(ErrorType.ROLE_MUST_BE_INTEGER.getMessage());
                    }
                    break;

                default:
                    throw new IllegalArgumentException(ErrorType.FIELD_NOT_RECOGNIZED.getMessage());
            }
        });
    }



}
