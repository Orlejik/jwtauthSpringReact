package com.art.jwt.backend.Controllers;


import com.art.jwt.backend.Config.UserAuthProvider;
import com.art.jwt.backend.Services.AuthenticationService;
import com.art.jwt.backend.Services.UserService;
import com.art.jwt.backend.dto.CredentialsDto;
import com.art.jwt.backend.dto.JwtAuthenticationResponse;
import com.art.jwt.backend.dto.SignUpDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "User registration")
    @CrossOrigin
    @PostMapping("/register")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpDto request) {
        System.out.println(request.getLogin());
        System.out.println(request.getEmail());
        System.out.println(request.getFirstName());
        System.out.println(request.getLastName());
        System.out.println(request.getPassword());
        System.out.println("User registration successful");
        System.out.println("The full request is : "+ request);
        return authenticationService.signUp(request);
    }

    @Operation(summary = "User login")
    @CrossOrigin
    @PostMapping("/login")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid CredentialsDto request) {
        System.out.println(request.getLogin());
        System.out.println(request.getPassword());
        System.out.println("User registration successful");
        System.out.println("The full request is : "+ request);
        return authenticationService.signIn(request);
    }
}
