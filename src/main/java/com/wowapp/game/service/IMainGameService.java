package com.wowapp.game.service;

import com.wowapp.game.dto.MetadataInfo;
import com.wowapp.game.dto.User;
import com.wowapp.game.enums.GameType;
import com.wowapp.game.enums.RoundTypeResult;

public interface IMainGameService {

    void saveGameInfo(MetadataInfo gameInfo);

    GameType getTypeOfGame();

    Boolean isPlayNextGame();

    void printHelloMessage();

    void printUserStatictics(MetadataInfo gameInfo);

    Integer getRoundCountUserInput();

    RoundTypeResult startPlayManualRound(Integer roundCount);

    RoundTypeResult startPlayAuthomaticRound(Integer roundCount);

    void displayMessageToScreen(String message);

    void displayBeginNextRound();

    void displayEndNextRound();

    User loginOrRegisterUser() ;

}
