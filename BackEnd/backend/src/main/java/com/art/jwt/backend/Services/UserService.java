package com.art.jwt.backend.Services;

import com.art.jwt.backend.Enteties.User;
import com.art.jwt.backend.Exceptions.AppException;
import com.art.jwt.backend.Mappers.UserMapper;
import com.art.jwt.backend.Repositories.UserRepository;
import com.art.jwt.backend.dto.CredentialsDto;
import com.art.jwt.backend.dto.SignUpDto;
import com.art.jwt.backend.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDTO findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));

        return userMapper.toUserDto(user);
    }

    public UserDTO login(CredentialsDto credentialsDto) {

        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }

        throw new AppException("Wrong password", HttpStatus.UNAUTHORIZED);
    }

    public UserDTO register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());

        if (optionalUser.isPresent()) {
            throw new AppException("User already exists", HttpStatus.CONFLICT);
        }
        User user = userMapper.signUpUser(userDto);

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }
}
