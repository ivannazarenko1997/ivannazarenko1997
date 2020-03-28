package com.wowapp.game.service;

import com.wowapp.game.dto.User;

/**
 * Created by pek on 2/20/2019.
 */
public interface IUserService {
    User findByLogin(String login);

    void save (User user);

    void update (User updaterUser);

    Boolean saveDataOnGameClose();
}
