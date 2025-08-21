package com.serviciosRosario.ServiciosRosario.security.filter;

import com.serviciosRosario.ServiciosRosario.entity.user.User;
import com.serviciosRosario.ServiciosRosario.security.service.JwtService;
import com.serviciosRosario.ServiciosRosario.service.user.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Obtener encabezado HTTP Authorization
        String authorizationHeader = request.getHeader("Authorization");

        // Valida si el encabezado está presente y empieza con "Bearer"
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Obtener el JWT desde el header (dsp de Bearer)
        String jwt = authorizationHeader.substring(7);

        // 3. Obtengo el subject/username desde el token
        // Esta accion valida el formato del token, firma y fecha de expiración
        String username = jwtService.extractUsername(jwt);

        // 4. Setea objeto Authentication dentro del SecurityContextHolder
        User user = userService.findOneByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, user.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 5. Ejecuta el resto de filtros
        filterChain.doFilter(request, response);
    }
}
