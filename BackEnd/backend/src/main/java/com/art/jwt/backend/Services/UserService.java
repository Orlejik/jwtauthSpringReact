package com.art.jwt.backend.Services;

import com.art.jwt.backend.Enteties.Role;
import com.art.jwt.backend.Enteties.User;
import com.art.jwt.backend.Exceptions.AppException;
import com.art.jwt.backend.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public  User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User createUser(User user){
        if(userRepository.existsByLogin(user.getLogin())){
            throw new RuntimeException("Login already exists");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */

    public User getUserByLogin(String login){
        return userRepository.findByLogin(login)
                .orElseThrow(()->new AppException("User was not found", HttpStatus.NOT_FOUND));
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService(){
        return this::getUserByLogin;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */

    public User getCurrentUser(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByLogin(username);
    }

    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */

    @Deprecated
    public void getAdmin(){
        var user = getCurrentUser();
        user.setRolle(Role.ROLE_ADMIN);
        save(user);
    }

}
