package com.art.jwt.backend.Controllers;


import com.art.jwt.backend.Config.UserAuthProvider;
import com.art.jwt.backend.Services.UserService;
import com.art.jwt.backend.dto.CredentialsDto;
import com.art.jwt.backend.dto.SignUpDto;
import com.art.jwt.backend.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@RequiredArgsConstructor
@Controller
//@CrossOrigin
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody CredentialsDto credentialsDto) {
        UserDTO user = userService.login(credentialsDto);

        user.setToken(userAuthProvider.createToken(user.getLogin()));

        System.out.println("__________________________________________  TOKEN ON LOGIN ____________________________________________");
        System.out.println(user.getToken());
        System.out.println("_______________________________________________  TOKEN ____________________________________________");

        return ResponseEntity.ok(user);
    }
    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody SignUpDto signUpDto) {
        UserDTO user = userService.register(signUpDto);
        user.setToken(userAuthProvider.createToken(user.getLogin()));
        System.out.println("_________________________________________  TOKEN ON REGISTER ____________________________________________");
        System.out.println(user.getToken());
        System.out.println("_______________________________________________  TOKEN ____________________________________________");
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }
}
