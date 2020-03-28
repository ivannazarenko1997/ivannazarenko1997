package com.wowapp.game.service.impl;

import com.wowapp.game.exeption.PasswordIncorrectExeption;
import com.wowapp.game.exeption.UserNotFoundExeption;
import com.wowapp.game.dto.User;
import com.wowapp.game.service.ILoginService;
import com.wowapp.game.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * LoginServiceImpl - class contains
 * functionality for User login into game.
 */
@Service("loginService")
public class LoginServiceImpl implements ILoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);
    private static final String USER_NOT_FOUND_MESSSAGE = "user_not_found_message";
    private static final String USER_PASSWORD_INCORRECT_MESSAGE = "user_password_incorrect";

    @Autowired
    private IUserService userService;

    public Boolean checkUserLoginPassword(String login, String password)
            throws UserNotFoundExeption, PasswordIncorrectExeption {
        LOGGER.info("<< checkUserLoginPassword");
        User findedUser = Optional.ofNullable(getUserData(login))
                .orElseThrow(() -> new UserNotFoundExeption(USER_NOT_FOUND_MESSSAGE));

        return Optional.ofNullable(isCorrectPassword(findedUser, password))
                .orElseThrow(() -> new PasswordIncorrectExeption(USER_PASSWORD_INCORRECT_MESSAGE));
    }

    private Boolean isCorrectPassword(User findedUser, String password) {
        LOGGER.info("<< isCorrectPassword");
        return (checkEmptyVariable(findedUser.getPassword()) &&
                password.equals(findedUser.getPassword())) ? true : null;
    }

    public User getUserData(String login) {
        return userService.findByLogin(login);
    }

    private Boolean checkEmptyVariable(String chekVariable) {
        return (chekVariable != null && !chekVariable.isEmpty()) ? true : false;
    }

}
