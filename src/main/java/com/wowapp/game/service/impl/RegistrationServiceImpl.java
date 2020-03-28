package com.wowapp.game.service.impl;

import com.wowapp.game.exeption.IncorrectLoginExeption;
import com.wowapp.game.exeption.PasswordCredentionalExeption;
import com.wowapp.game.exeption.UserNotFoundExeption;
import com.wowapp.game.dto.User;
import com.wowapp.game.service.IRegistrationService;
import com.wowapp.game.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RegistrationServiceImpl - class contains
 * functionality for Registration user into game.
 */
@Service("registrationService")
public class RegistrationServiceImpl implements IRegistrationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationServiceImpl.class);
    private static final String LOGIN_PASSWORD_EMPTY_MESSAGE = "login_or_password_empty";
    private static final String USER_PASSWORD_INCORRECT_CREDENTIONALS_MESSAGE =  "user_password_credentionals";

    @Autowired
    private IUserService userService;

    private Boolean validatePasswordUser(String password) {
        return password.length() >= 8 ? true : false;
    }

    private Boolean validateLoginUser(String login) {
        return login.length() > 4 ? true : false;
    }

    public Boolean checkUserExists(String login) throws UserNotFoundExeption {
        LOGGER.info("<< checkUserExists");
        return validateLoginUser(login) && userService.findByLogin(login) != null;
    }

    public Boolean validateUserNamePassword(String login, String password)
            throws IncorrectLoginExeption, PasswordCredentionalExeption {
        LOGGER.info("<< validateUserNamePassword");
        if (!validateLoginUser(login)) {
            throw new IncorrectLoginExeption(LOGIN_PASSWORD_EMPTY_MESSAGE);
        }
        if (!validatePasswordUser(password)) {
            throw new PasswordCredentionalExeption(USER_PASSWORD_INCORRECT_CREDENTIONALS_MESSAGE);
        }
        return true;
    }

    public void registerUser(String login, String password) {
        LOGGER.info("<< registerUser");
        User userDataSource = User.builder()
                .login(login)
                .password(password)
                .roundid(0)
                .draws(0)
                .wins(0)
                .loses(0)   // .lastTimeLogin(new DateTime())
                .build();
        userService.save(userDataSource);
    }

}
