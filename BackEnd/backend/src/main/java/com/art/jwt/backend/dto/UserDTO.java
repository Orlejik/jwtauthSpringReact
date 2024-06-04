package com.art.jwt.backend.dto;


import com.art.jwt.backend.Enteties.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(description = "Registration request")
public class UserDTO {


    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private Role rolle;
    private String token;
}
