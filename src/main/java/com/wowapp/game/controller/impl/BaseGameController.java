package com.wowapp.game.controller.impl;

import com.wowapp.game.dto.MetadataInfo;
import com.wowapp.game.dto.User;
import com.wowapp.game.enums.GameType;
import com.wowapp.game.enums.PlayAttribute;
import com.wowapp.game.enums.RoundTypeResult;
import com.wowapp.game.service.IMainGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/*
 BaseGameController class   contains   functionality for start game and determine game logic.
 */
public class BaseGameController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseGameController.class);
    @Value("${timeout}") /* time out between rounds of automatic games from application.properties file*/
    private Integer timeout = 5;
    @Autowired
    protected IMainGameService mainGameService;
    protected Integer roundCount = 0;
    protected static final  MetadataInfo GAME_INFO = new  MetadataInfo();

    protected void setTypeOfGameFromDialog() {
        GameType gameType = mainGameService.getTypeOfGame();
        if (GameType.TERMINATE.equals(gameType)) {
            saveGameInfo();
        }
        setGameType(gameType);
    }

    protected void setGameUserInfo() {
        LOGGER.info("<<setGameUserInfo");
        User  infoUser = loginOrRegisterUser();
        GAME_INFO.setUserName(infoUser.getLogin());
        GAME_INFO.setUserLouse(infoUser.getLoses());
        GAME_INFO.setUserDraws(infoUser.getDraws());
        GAME_INFO.setUserWins(infoUser.getWins());
        GAME_INFO.setRoundId(infoUser.getRoundid());
        mainGameService.printUserStatictics(GAME_INFO);
    }

    protected void changeGameResults(RoundTypeResult result) {
        LOGGER.info("<<changeGameResults");
        switch (result) {
            case WIN:
                GAME_INFO.setUserWins(increment(GAME_INFO.getUserWins()));
                displayMessageToScreen("User wins");
                break;
            case LOSE:
                GAME_INFO.setUserLouse(increment(GAME_INFO.getUserLouse()));
                displayMessageToScreen("Computer wins");
                break;
            case DRAW:
                GAME_INFO.setUserDraws(increment(GAME_INFO.getUserDraws()));
                displayMessageToScreen("Draws");
                break;
        }
    }

    private Integer increment(Integer value){
        return ++value;
    }

    private void displayMessageToScreen(String message){
        mainGameService.displayMessageToScreen(message);
    }
    protected void playAutomaticRound(Integer round) {
        LOGGER.info("<<playAutomaticRound");
        mainGameService.displayBeginNextRound();
        displayMessageToScreen("Play round " + round + " from " + roundCount + " rounds ");
        changeGameResults(mainGameService.startPlayAuthomaticRound(round));
        sleepForNextRound(timeout);
        mainGameService.displayEndNextRound();
    }

    protected void playManualRound(Integer round) {
        LOGGER.info("<<playManualRound");
        mainGameService.displayBeginNextRound();
        displayMessageToScreen("Play round " + round + " from " + roundCount + " rounds ");
        RoundTypeResult roundResult = mainGameService.startPlayManualRound(round);
        if (RoundTypeResult.TERMINATE.equals(roundResult)) {
             saveGameInfo();
        }
        changeGameResults(roundResult);
        mainGameService.displayEndNextRound();
    }

    protected void sleepForNextRound(Integer miliSec) {
        try {
            Thread.sleep(miliSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void saveGameInfo() {
        mainGameService.saveGameInfo(GAME_INFO);
    }

    protected Boolean isPlayNextGame() {
        return mainGameService.isPlayNextGame();
    }

    protected void printHelloMessage() {
        mainGameService.printHelloMessage();
    }

    protected User loginOrRegisterUser() {
        return mainGameService.loginOrRegisterUser();
    }

    protected void setNumberOfRounds() {
        setGameRoundCount(mainGameService.getRoundCountUserInput());
    }

    protected void setGameRoundCount(Integer round) {
        this.roundCount = round;
    }

    protected Integer getNumbersForGame() {
        return this.roundCount;
    }

    protected void setGameType(GameType type) {
        GAME_INFO.setGameType(type);
    }

    protected void setGameCount() {
        GAME_INFO.setRoundId(GAME_INFO.getRoundId() + getNumbersForGame());
    }

}
