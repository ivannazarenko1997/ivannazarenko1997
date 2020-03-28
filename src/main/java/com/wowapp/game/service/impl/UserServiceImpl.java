package com.wowapp.game.service.impl;

import com.wowapp.game.dto.User;
import com.wowapp.game.repository.IGameDataSourceRepository;
import com.wowapp.game.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ScreenInterfaceImpl - class contains
 *  functionality for find, save and update current user
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainGameServiceImpl.class);

    @Autowired
    private IGameDataSourceRepository dataRepository;

    @Transactional
    public User findByLogin(String login) {
        return dataRepository.getDataSourceUserMap().get(login);
    }

    @Transactional
    public void save(User user) {
        LOGGER.info("<< saveUser");
        String userLogin = user.getLogin();
        dataRepository.getDataSourceUserMap().put(userLogin, user);
    }

    @Transactional
    public void update(User updaterUser) {
        LOGGER.info("<< updateUser");
        User userFromRepo = findByLogin(updaterUser.getLogin());
        userFromRepo.setDraws(updaterUser.getDraws());
        userFromRepo.setWins(updaterUser.getWins());
        userFromRepo.setLoses(updaterUser.getLoses());  // .lastTimeLogin(new DateTime())
        userFromRepo.setRoundid(updaterUser.getRoundid());

        save(userFromRepo);
    }

    public Boolean saveDataOnGameClose() {
        return dataRepository.saveDataOnGameClose();
    }
}
