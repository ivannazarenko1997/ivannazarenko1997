package com.wowapp.game.service;

import com.wowapp.game.dto.User;

public interface ILoginService {
    Boolean checkUserLoginPassword(String login, String password);

    User getUserData(String login);
}
