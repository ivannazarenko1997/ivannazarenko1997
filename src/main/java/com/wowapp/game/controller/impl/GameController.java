package com.wowapp.game.controller.impl;

import com.wowapp.game.controller.IGameController;
import com.wowapp.game.enums.GameType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 GameController classs  contains   functionality for start game and determine game logic.
 */
@Service("gameController")
public class GameController extends BaseGameController implements IGameController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);


    /* startGame method - entry point into game  */
    @Transactional
    public void startGame() {
        LOGGER.info("<<startGame");
        printHelloMessage();
        setGameUserInfo();
        playNewOrNextGame();
    }

    private void playNewOrNextGame() {
        LOGGER.info("<<nextGamePlayRound");
        setTypeOfGameFromDialog();

        setNumberOfRounds();

        playGameRounds();

        setGameCount();
        if (isPlayNextGame()) {
            playNewOrNextGame();
        } else {
            saveGameInfo();
        }
    }
    @Transactional
    @Async("processExecutor")
    private void playGameRounds() {
        LOGGER.info("<<playGameRounds");

        for (int i = 1; i < getNumbersForGame() + 1; i++) {
            if (GAME_INFO.getGameType().equals(GameType.AUTHOMATIC)) {
                playAutomaticRound(i);
            } else {
                playManualRound(i);
            }
        }
    }
}
