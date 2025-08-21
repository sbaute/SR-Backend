package com.serviciosRosario.ServiciosRosario.security.service;

import com.serviciosRosario.ServiciosRosario.security.DTO.AuthenticationRequest;
import com.serviciosRosario.ServiciosRosario.security.DTO.AuthenticationResponse;
import com.serviciosRosario.ServiciosRosario.entity.user.DTO.UserRegisterResponse;
import com.serviciosRosario.ServiciosRosario.entity.user.DTO.UserRegisterRequest;
import com.serviciosRosario.ServiciosRosario.entity.user.User;
import com.serviciosRosario.ServiciosRosario.service.user.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private  UserServiceImpl userServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;


    public UserRegisterResponse registerOneUser (UserRegisterRequest userRegisterRequest){

       User user = userServiceImpl.registerUser(userRegisterRequest);

       UserRegisterResponse userDto = new UserRegisterResponse();

       userDto.setId(user.getId());
       userDto.setName(user.getName());
       userDto.setUsername(user.getUsername());
       userDto.setRole(user.getRole() != null ? user.getRole().getName() : null);
       userDto.setEmail(user.getEmail());
       userDto.setPhone(user.getPhone());
       userDto.setAddress(user.getAddress());
       String jwt = jwtService.generateJwtToken(user, generateExtraClaims(user));
       userDto.setJwt(jwt);

       return userDto;
   }

    private Map<String, Object> generateExtraClaims(User user) {

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().getName());
        extraClaims.put("authorities", user.getAuthorities());

        return extraClaims;
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        );

        authenticationManager.authenticate(authentication);

        UserDetails user = userServiceImpl.findOneByUsername(authRequest.getUsername()).get();
        String jwt = jwtService.generateJwtToken(user, generateExtraClaims((User)user));
        String role = ((User) user).getRole().getName();

        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setJwt(jwt);
        authResponse.setRole(role);

        return authResponse;
    }


    public boolean validateToken(String jwt) {
        try {
            jwtService.extractUsername(jwt);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public User findLoggedInUser() {
        Authentication auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        return userServiceImpl.findOneByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
