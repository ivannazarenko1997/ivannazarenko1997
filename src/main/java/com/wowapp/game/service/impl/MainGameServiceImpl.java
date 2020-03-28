package com.wowapp.game.service.impl;

import com.wowapp.game.exeption.IncorrectLoginExeption;
import com.wowapp.game.exeption.PasswordCredentionalExeption;
import com.wowapp.game.exeption.PasswordIncorrectExeption;
import com.wowapp.game.exeption.UserNotFoundExeption;
import com.wowapp.game.dto.MetadataInfo;
import com.wowapp.game.dto.User;
import com.wowapp.game.enums.GameType;
import com.wowapp.game.enums.PlayAttribute;
import com.wowapp.game.enums.RoundTypeResult;
import com.wowapp.game.enums.UserAction;
import com.wowapp.game.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * MainGameServiceImpl - class contains
 * functionality for Main Game Service
 as play next game, next round and game exit  from game.
 */
@Service("mainGameService")
public class MainGameServiceImpl extends MainBaseGameService implements IMainGameService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainGameServiceImpl.class);
    private static final String USER_ALLREADY_EXIST_ERROR_MESSAGE = "user_allready_exists_message";
    private static final String USER_LOGIN_MESSAGE = "user_login_action";
    private static final String USER_REGISTER_MESSAGE = "user_register_action";

    @Autowired
    private IUserService userService;
    @Autowired
    private ILoginService loginService;
    @Autowired
    private IRegistrationService registrationService;
    @Autowired
    private IScreenInterface screenInterface;

    public void printUserStatictics(MetadataInfo gameInfo) {
        LOGGER.info("<< printUserLoggedInfo");
        screenInterface.printUserStatistics(gameInfo);
    }

    @Transactional
    public void saveGameInfo(MetadataInfo gameInfo) {
        LOGGER.info("<< saveGameInfo");
        userService.update(convertMetaDataToUser(gameInfo));
        printUserStatictics(gameInfo);

        screenInterface.displayExitMessage();
        if (userService.saveDataOnGameClose()) {
            System.exit(0);
        }
    }

    public Boolean isPlayNextGame() {
        return screenInterface.userPlayNextGame();
    }

    @Transactional
    public RoundTypeResult startPlayManualRound(Integer roundCount) {
        LOGGER.info("<< startPlayManualRound");
        PlayAttribute userStep = screenInterface.getUserStepFromScreen();
        if (PlayAttribute.TERMINATE.equals(userStep)) {
            return RoundTypeResult.TERMINATE;
        }
        screenInterface.printGameUserStep(userStep);
        PlayAttribute computerStep = getComputerStep();
        screenInterface.printGameComputerStep(computerStep);
        return getRoundWinner(userStep, computerStep);
    }


    public void displayMessageToScreen(String message) {
        LOGGER.info("<< displayMessageToScreen");
        screenInterface.displayMessageToScreen(message);
    }
    @Transactional
    public User loginOrRegisterUser() {
        LOGGER.info("<< loginOrRegisterUser");
        UserAction userAtcion = screenInterface.userLoginOrRegistrationStep();

        if (UserAction.LOGIN.equals(userAtcion)) {//login user
            screenInterface.displayInfoMessage(USER_LOGIN_MESSAGE);
        } else {//register user
            screenInterface.displayInfoMessage(USER_REGISTER_MESSAGE);
        }
        return makeLoginOrRegisterUser(userAtcion);
    }

    @Transactional
    public RoundTypeResult startPlayAuthomaticRound(Integer roundCount) {
        LOGGER.info("<< startPlayAuthomaticRound");
        PlayAttribute userStep = getUserBadAuthomaticStep();
        screenInterface.printGameUserStep(userStep);
        PlayAttribute computerStep = getComputerStep();
        screenInterface.printGameComputerStep(computerStep);
        return getRoundWinner(userStep, computerStep);
    }

    private User makeLoginOrRegisterUser(UserAction loginOrRegister) { //true -login, false -register
        LOGGER.info("<< makeLoginUserOrRegisterUser");
        try {
            String login = screenInterface.getLoginUserFromInput();
            if (UserAction.LOGIN.equals(loginOrRegister)) { //login user
                String password = screenInterface.getPasswordUserFromInput();
                loginService.checkUserLoginPassword(login, password);
            } else { //register user
                if (registrationService.checkUserExists(login)) {
                    screenInterface.displayErrorMessage(USER_ALLREADY_EXIST_ERROR_MESSAGE);
                    return makeNextStepUserLoginOrRegister(loginOrRegister);
                }
                String password = screenInterface.getPasswordUserFromInput();

                if (!registrationService.validateUserNamePassword(login, password)) {
                    return makeNextStepUserLoginOrRegister(loginOrRegister);
                }
                registrationService.registerUser(login, password);
            }
            return loginService.getUserData(login);
        } catch (UserNotFoundExeption | PasswordIncorrectExeption
                | IncorrectLoginExeption | PasswordCredentionalExeption ex) {
            screenInterface.displayErrorMessage(ex.getMessage());
            return makeNextStepUserLoginOrRegister(loginOrRegister);
        }
    }

    private Boolean makeInputAgainOrExit() {
        return screenInterface.makeInputAgainOrExit();
    }

    private User makeNextStepUserLoginOrRegister(UserAction userAction) {
        LOGGER.info("<< makeNextStepUserLoginOrRegister");
        if (makeInputAgainOrExit()) {
            return makeLoginOrRegisterUser(userAction);
        } else {
            return loginOrRegisterUser();
        }
    }

    public Integer getRoundCountUserInput() {
        return screenInterface.getUserRoundCountInput();
    }

    public void displayBeginNextRound() {
        screenInterface.displayNextRoundMessage();
    }

    public void displayEndNextRound() {
        screenInterface.displayEndRoundMessage();
    }

    public void printHelloMessage() {
        screenInterface.printHelloMessage();
    }

    public GameType getTypeOfGame() {
        return screenInterface.userDialogChooseTypeOfGame();
    }

}
