package com.art.jwt.backend.Services;

import com.art.jwt.backend.Config.UserAuthProvider;
import com.art.jwt.backend.Enteties.Role;
import com.art.jwt.backend.Enteties.User;
import com.art.jwt.backend.dto.SignInDto;
import com.art.jwt.backend.dto.JwtAuthenticationResponse;
import com.art.jwt.backend.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final UserAuthProvider jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */

    public JwtAuthenticationResponse signUp(SignUpDto request){
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .login(request.getLogin())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rolle(Role.ROLE_USER)
                .build();

        userService.createUser(user);

        var jwt = jwtService.generateSecretToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */

    public  JwtAuthenticationResponse signIn(SignInDto request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getLogin(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getLogin());

        var jwt = jwtService.generateSecretToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

}
