package com.art.jwt.backend.Mappers;

import com.art.jwt.backend.Enteties.User;
import com.art.jwt.backend.dto.SignUpDto;
import com.art.jwt.backend.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpUser(SignUpDto userDto);
}
