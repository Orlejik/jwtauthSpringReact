package com.art.jwt.backend.Config;

import com.art.jwt.backend.Services.UserService;
import com.art.jwt.backend.dto.UserDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    private final UserService userService;

    @PostConstruct
    protected void init(){
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String login){

        Date now = new Date();
        Date validity = new Date(now.getTime()+3_600_000_000L);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        String token  = JWT.create()
                .withIssuer(login)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(Algorithm.HMAC256(secretKey));

        System.out.println("___________________________________________________ TOKEN ___________________________________________");
        System.out.println(token);
        System.out.println("___________________________________________________ TOKEN ___________________________________________");

        return  token;
    }


    //here can be an error due to returns "UsernamePasswordAuthenticationToken" but in video this method return "Authentication"
    public Authentication validateToken(String token){
        JWTVerifier verifier=  JWT.require(Algorithm.HMAC256(secretKey)).withIssuer("http://localhost:3000").build();
        DecodedJWT decoded = verifier.verify(token);
        System.out.println("___________________________________________________ DECODED TOKEN ___________________________________________");
        System.out.println(decoded);
        System.out.println("___________________________________________________ DECODED TOKEN ___________________________________________");

        UserDTO user = userService.findByLogin(decoded.getIssuer());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

}
