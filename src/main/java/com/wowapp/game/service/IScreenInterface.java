package com.wowapp.game.service;


import com.wowapp.game.dto.MetadataInfo;
import com.wowapp.game.enums.GameType;
import com.wowapp.game.enums.PlayAttribute;
import com.wowapp.game.enums.UserAction;

import java.util.Scanner;

/**
 * Created by pek on 2/20/2019.
 */
public interface IScreenInterface {
    void displayMessageToScreen(String message);

    UserAction userLoginOrRegistrationStep();

    void printUserStatistics(MetadataInfo gameInfo);

    void displayInfoMessage(String message);

    void displayErrorMessage(String message);

    GameType userDialogChooseTypeOfGame();

    Boolean userPlayNextGame();

    void displayExitMessage();

    Boolean makeInputAgainOrExit();

    void printHelloMessage();

    String getLoginUserFromInput();

    String getPasswordUserFromInput();

    Integer getUserRoundCountInput();

    PlayAttribute getUserStepFromScreen();

    void printGameComputerStep(PlayAttribute stepAttribute);

    void printGameUserStep(PlayAttribute stepAttribute);

    void displayNextRoundMessage();

    void displayEndRoundMessage();

    void setScanner(Scanner inputScanner );

}
