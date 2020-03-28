package com.wowapp.game.service;

public interface IRegistrationService {
    void registerUser(String login, String password);

    Boolean validateUserNamePassword(String login, String password);

    Boolean checkUserExists(String login);

}
