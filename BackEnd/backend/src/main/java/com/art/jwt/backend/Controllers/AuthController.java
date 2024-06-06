package com.art.jwt.backend.Controllers;


import com.art.jwt.backend.Services.AuthenticationService;
import com.art.jwt.backend.dto.SignInDto;
import com.art.jwt.backend.dto.JwtAuthenticationResponse;
import com.art.jwt.backend.dto.SignUpDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "User registration")
    @PostMapping("/register")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpDto request) {
        try {
            return authenticationService.signUp(request);
        } catch (Exception e) {
            System.out.println("Error in JwtAuthenticationResponse signUp" + e.getMessage());
        }
        return null;
    }

    @Operation(summary = "User login")
    @PostMapping("/login")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInDto request) {
        try{
            return authenticationService.signIn(request);
        }catch (Exception e){
            System.out.println("Error in JwtAuthenticationResponse signIn" + e.getMessage());
        }
        return null;
    }
}
