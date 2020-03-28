package com.wowapp.game.service.impl;

import com.wowapp.game.dto.MetadataInfo;
import com.wowapp.game.enums.GameType;
import com.wowapp.game.enums.PlayAttribute;
import com.wowapp.game.enums.UserAction;
import com.wowapp.game.repository.IGameDataSourceRepository;
import com.wowapp.game.service.IScreenInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * ScreenInterfaceImpl - class contains
 * functionality for display messages to screen and user steps input
 */
@Service("screenInterface")
public class ScreenInterfaceImpl implements IScreenInterface {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ScreenInterfaceImpl.class);

    private static   Scanner inputScanner = new Scanner(System.in);

    private static final String SCREEN_LINE = "---------------------------------------------------------------------";
    private static final String SCREEN_BEGIN_NEXT_LINE = ("-------------------Next_Round-----------------------------");
    private static final String SCREEN_END_NEXT_LINE = ("-------------------Round_End------------------------------");
    private static final String SCREEN_POINT = " ******* ";
    private static final String HELLO_MESSAGE = "hello_game_message";
    private static final String GAME_TYPE_MESSAGE = "game_type_chouse_message";
    private static final String GAME_TYPE_RESULT_MESSAGE = "game_type_chouse_result";
    private static final String GAME_STEP_INCORRECT_MESSAGE = "incorrect_game_step_message";
    private static final String GAME_PLAY_QUESTION_MESSAGE = "game_play_question_message";
    private static final String GAME_EXIT_MESSAGE = "game_exit_message";
    private static final String GAME_LOGIN_ASK_MESSAGE = "game_login_account_message";
    private static final String LOGIN_USER_INPUT = "login_user_input";
    private static final String PASSWORD_USER_INPUT = "password_user_input";
    private static final String USER_ROW_COUNT_INPUT = "user_row_count_input";
    private static final String ERROR_MESSAGE = "error_message_input";
    private static final String ENTER_VALUE_AGAIN_MESSAGE = "enter_value_again_message";
    private static final String USER_STEP_MAKE = "user_step_make";
    private static final String REGEX_INTEGER = "\\d+";

    private static final Map<PlayAttribute, String> STEPS_DECRIPTION = new HashMap<PlayAttribute, String>() {
        {
            put(PlayAttribute.ROCK, "Rock");
            put(PlayAttribute.PAPER, "Paper");
            put(PlayAttribute.SCISSOR, "Scissors");
        }
    };

    public void setScanner(Scanner inputScanner ) {
        this.inputScanner = inputScanner;
    }

    @Autowired
    private IGameDataSourceRepository dataRepository;

    public ScreenInterfaceImpl() {
        LOGGER.info("<<Initialize screen interface");
    }

    private String getUserInputFromScreen() {
        return inputScanner.nextLine();
    }

    @Override
    public void displayMessageToScreen(String message) {
        System.out.println(message);
    }

    public void printUserStatistics(MetadataInfo gameInfo) {
        LOGGER.info("<< printUserResultGameOnExit");
        String message = "Statictics for user '" + gameInfo.getUserName()
                + "' ; wins = " + gameInfo.getUserWins() + ", loses = "
                + gameInfo.getUserLouse() + ", draws = " + gameInfo.getUserDraws();
        printScreenLine();
        displayOnScreen(message);
        printScreenLine();
    }

    public void displayErrorMessage(String message) {
        displayStepMessage(getMetaData(ERROR_MESSAGE));
        displayMessageToScreen(getMetaData(message));
    }

    public void displayInfoMessage(String message) {
        displayMessageToScreen(getMetaData(message));
    }

    private void printScreenLine() {
        System.out.println(SCREEN_LINE);
    }

    private void displayOnScreen(String message) {
        System.out.print(SCREEN_POINT);
        System.out.print(message);
        System.out.println(SCREEN_POINT);
    }

    private void displaySimpleMessage(String message) {
        System.out.print(message);
    }

    private void displayStepMessage(String message) {
        System.out.print(message + ": ");
    }

    private String getMetaData(String message) {
        return dataRepository.getMetaDataMap().get(message);
    }

    private String getParceMetaData(String message, String parceMessage) {
        return String.format(dataRepository.getMetaDataMap().get(message), parceMessage);
    }

    public void printHelloMessage() {
        printScreenLine();
        displayOnScreen(getMetaData(HELLO_MESSAGE));
        printScreenLine();
    }

    private void printChooseGameTypeMessage() {
        displayStepMessage(getMetaData(GAME_TYPE_MESSAGE));
    }

    private void printUserChooseGameTypeResult(String gameResult) {
        displaySimpleMessage(getParceMetaData(GAME_TYPE_RESULT_MESSAGE, gameResult));
    }

    private GameType getTypeOfGameFromUser() {
        switch (getUserInputFromScreen()) {
            case "A":
                return GameType.AUTHOMATIC;
            case "M":
                return GameType.MANUAL;
            case "T":
                return GameType.TERMINATE;
            default:
                displayStepMessage(getMetaData(GAME_STEP_INCORRECT_MESSAGE));
                return getTypeOfGameFromUser();
        }
    }

    public GameType userDialogChooseTypeOfGame() {
        LOGGER.info("<< userDialogChooseTypeOfGame");
        printChooseGameTypeMessage();
        GameType userChoise = getTypeOfGameFromUser();
        if (GameType.TERMINATE.equals(userChoise)) {
            return GameType.TERMINATE;
        }
        printUserChooseGameTypeResult(userChoise.getType());
        return userChoise;
    }

    private void printPlayNextGameOrExitMessage() {
        displayStepMessage(getMetaData(GAME_PLAY_QUESTION_MESSAGE));
    }

    private void makeInputAgainOrExitMessage() {
        displayStepMessage(getMetaData(ENTER_VALUE_AGAIN_MESSAGE));
    }

    private Boolean askYesNoUserQuestionForNexStep() {
        LOGGER.info("<< displayUserDefaultQuestionForNextGameStep");
        switch (getUserInputFromScreen()) {
            case "Y":
                return true;
            case "N":
                return false;
            default:
                displayStepMessage(getMetaData(GAME_STEP_INCORRECT_MESSAGE));
                return askYesNoUserQuestionForNexStep();
        }
    }

    private PlayAttribute makeUserStepByPlayGame(String message) {
        LOGGER.info("<< makeUserStepByPlayGame");
        displayStepMessage(message);
        switch (getUserInputFromScreen()) {
            case "P":
                return PlayAttribute.PAPER;
            case "R":
                return PlayAttribute.ROCK;
            case "S":
                return PlayAttribute.SCISSOR;
            case "T":
                return PlayAttribute.TERMINATE;
            default:
                return makeUserStepByPlayGame(message);
        }
    }

    public void displayExitMessage() {
        printScreenLine();
        displayOnScreen(getMetaData(GAME_EXIT_MESSAGE));
        printScreenLine();
    }

    public void printLoginAccountMessage() {
        displayStepMessage(getMetaData(GAME_LOGIN_ASK_MESSAGE));
    }

    public UserAction userLoginOrRegistrationStep() {
        return askUserLoginOrRegister();
    }

    private UserAction askUserLoginOrRegister() {
        LOGGER.info("<< displayUserDefaultQuestionForNextGameStep");
        printLoginAccountMessage();
        switch (getUserInputFromScreen()) {
            case "Y":
                return UserAction.LOGIN;
            case "N":
                return UserAction.REGISTER;
            default:
                displayStepMessage(getMetaData(GAME_STEP_INCORRECT_MESSAGE));
                return askUserLoginOrRegister();
        }
    }

    public Boolean userPlayNextGame() {
        printPlayNextGameOrExitMessage();
        return askYesNoUserQuestionForNexStep();
    }

    public Boolean makeInputAgainOrExit() {
        makeInputAgainOrExitMessage();
        return askYesNoUserQuestionForNexStep();
    }

    private String checkNotEmptyInputValue(String message) {
        displayStepMessage(message);
        String inputValue = getUserInputFromScreen();
        return inputValue.isEmpty() ? checkNotEmptyInputValue(message) : inputValue;
    }

    public String getLoginUserFromInput() {
        String message = getMetaData(LOGIN_USER_INPUT);
        return checkNotEmptyInputValue(message);
    }

    public String getPasswordUserFromInput() {
        String message = getMetaData(PASSWORD_USER_INPUT);
        return checkNotEmptyInputValue(message);
    }

    private Integer checkNotEmptyIntegerInputValue(String message) {
        displayStepMessage(message);
        String inputValue = getUserInputFromScreen();
        return (inputValue.isEmpty() || !inputValue.matches(REGEX_INTEGER))
                ? checkNotEmptyIntegerInputValue(message) : Integer.valueOf(inputValue);
    }

    public Integer getUserRoundCountInput() {
        String message = getMetaData(USER_ROW_COUNT_INPUT);
        return checkNotEmptyIntegerInputValue(message);
    }

    public PlayAttribute getUserStepFromScreen() {
        String message = getMetaData(USER_STEP_MAKE);
        return makeUserStepByPlayGame(message);
    }

    public void printGameComputerStep(PlayAttribute stepAttribute) {
        displayMessageToScreen("Computer step: " + STEPS_DECRIPTION.get(stepAttribute));
    }

    public void printGameUserStep(PlayAttribute stepAttribute) {
        displayMessageToScreen("User step: " + STEPS_DECRIPTION.get(stepAttribute));
    }

    public void displayNextRoundMessage() {
        displayMessageToScreen(SCREEN_BEGIN_NEXT_LINE);
    }

    public void displayEndRoundMessage() {
        displayMessageToScreen(SCREEN_END_NEXT_LINE);
    }
}

