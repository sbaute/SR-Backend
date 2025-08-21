package com.serviciosRosario.ServiciosRosario.security.controller;

import com.serviciosRosario.ServiciosRosario.security.DTO.AuthenticationRequest;
import com.serviciosRosario.ServiciosRosario.security.DTO.AuthenticationResponse;
import com.serviciosRosario.ServiciosRosario.entity.user.User;
import com.serviciosRosario.ServiciosRosario.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt){
        boolean isTokenValid = authService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse response = authService.login(authenticationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> findMyProfile(){
        User user = authService.findLoggedInUser();
        return ResponseEntity.ok(user);
    }


}
