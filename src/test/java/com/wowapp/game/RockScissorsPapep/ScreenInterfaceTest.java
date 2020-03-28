package com.wowapp.game.RockScissorsPapep;

import com.wowapp.game.enums.GameType;
import com.wowapp.game.enums.PlayAttribute;
import com.wowapp.game.repository.IGameDataSourceRepository;
import com.wowapp.game.service.IScreenInterface;
import com.wowapp.game.service.impl.ScreenInterfaceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * Created by pek on 2/23/2019.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest()
public class ScreenInterfaceTest {
    @Mock
    private IGameDataSourceRepository dataRepository;
    @InjectMocks
    private IScreenInterface service = new ScreenInterfaceImpl();

    private static final String LOGIN = "login1";
    private static final String PASSWORD = "password1";
    private static final GameType MANUAL_GAME = GameType.MANUAL;
    private static final GameType AUTO_GAME = GameType.AUTHOMATIC;
    private static final String MANUAL_GAME_INPUT = "M";
    private static final String AUTO_GAME_INPUT = "A";
    private static final Boolean EXPECTED_POSITIVE = true;
    private static final Boolean EXPECTED_NEGATIVE = false;
    private static final String PAPER = "P";
    private static final String ROCK = "R";
    private static final String SCISSOR = "S";
    private static final String YES_ANSWER = "Y";
    private static final String NO_ANSWER = "N";
    private static final String WRONG_STEP = "WRONG";
    private static final String WRONG_EMPTY_STEP = "";
    private static final String GAME_TYPE_MESSAGE = "game_type_chouse_message";
    private static final String GAME_TYPE_RESULT_MESSAGE = "game_type_chouse_result";
    private static final String USER_STEP_MAKE = "user_step_make";
    private static final String GAME_LOGIN_ASK_MESSAGE = "game_login_account_message";
    private static final  Map<String, String> mapMeatData= new HashMap<String, String>(){{
        put(GAME_TYPE_MESSAGE,GAME_TYPE_MESSAGE);
        put(GAME_TYPE_RESULT_MESSAGE,GAME_TYPE_RESULT_MESSAGE);
        put(USER_STEP_MAKE,USER_STEP_MAKE);
        put(GAME_LOGIN_ASK_MESSAGE,GAME_LOGIN_ASK_MESSAGE);
    }};

    @Test
    public void testPasswordUserFromInput() {
        Scanner sc = new Scanner(PASSWORD);
        service.setScanner(sc);
        String expectedPassword = service.getPasswordUserFromInput();
        assertEquals(expectedPassword, PASSWORD);
        sc.close();
    }

    @Test
    public void testLoginUserFromInput() {
        Scanner sc = new Scanner(LOGIN);
        service.setScanner(sc);
        String expectedPassword = service.getLoginUserFromInput();
        assertEquals(expectedPassword, LOGIN);
        sc.close();
    }

    @Test
    public void testDialogChooseAutomaticTypeOfGame() {
        Scanner sc = new Scanner(AUTO_GAME_INPUT);
        service.setScanner(sc);
        Mockito.when(dataRepository.getMetaDataMap()).thenReturn(mapMeatData);
        GameType expectedGameType = service.userDialogChooseTypeOfGame();
        assertEquals(expectedGameType, AUTO_GAME);
        sc.close();
    }

    @Test
    public void testDialogChooseManualTypeOfGame() {
        Scanner sc = new Scanner(MANUAL_GAME_INPUT);

        service.setScanner(sc);
        Mockito.when(dataRepository.getMetaDataMap()).thenReturn(mapMeatData);
        GameType expectedGameType = service.userDialogChooseTypeOfGame();
        assertEquals(expectedGameType, MANUAL_GAME);
        sc.close();
    }

    @Test
    public void testUserStepFromScreenThenUserSelectPaper() {
        Scanner sc = new Scanner(PAPER);
        service.setScanner(sc);
        Mockito.when(dataRepository.getMetaDataMap()).thenReturn(mapMeatData);
        PlayAttribute expectedGameType = service.getUserStepFromScreen();
        assertEquals(expectedGameType, PlayAttribute.PAPER);
        sc.close();
    }

    @Test
    public void testUserStepFromScreenThenUserSelectRock() {
        Scanner sc = new Scanner(ROCK);
        service.setScanner(sc);
        Mockito.when(dataRepository.getMetaDataMap()).thenReturn(mapMeatData);
        PlayAttribute expectedGameType = service.getUserStepFromScreen();
        assertEquals(expectedGameType, PlayAttribute.ROCK);
        sc.close();
    }

    @Test
    public void testUserStepFromScreenThenUserSelectScissors() {
        Scanner sc = new Scanner(SCISSOR);
        service.setScanner(sc);
        Mockito.when(dataRepository.getMetaDataMap()).thenReturn(mapMeatData);
        PlayAttribute expectedGameType = service.getUserStepFromScreen();
        assertEquals(expectedGameType, PlayAttribute.SCISSOR);
        sc.close();
    }

    @Test
    public void testUserPlayNextGamePositiveAnsver() {
        Scanner sc = new Scanner(YES_ANSWER);
        service.setScanner(sc);
        Mockito.when(dataRepository.getMetaDataMap()).thenReturn(mapMeatData);
        Boolean expected  = service.userPlayNextGame();
        assertEquals(expected, EXPECTED_POSITIVE);
        sc.close();
    }

    @Test
    public void testUserPlayNextGameNegativeeAnsver() {
        Scanner sc = new Scanner(NO_ANSWER);
        service.setScanner(sc);
        Mockito.when(dataRepository.getMetaDataMap()).thenReturn(mapMeatData);
        Boolean expected  = service.userPlayNextGame();
        assertEquals(expected, EXPECTED_NEGATIVE);
        sc.close();
    }

    @Test
    public void testMakeInputAgainOrExitPositiveAnsver() {
        Scanner sc = new Scanner(YES_ANSWER);
        service.setScanner(sc);
        Mockito.when(dataRepository.getMetaDataMap()).thenReturn(mapMeatData);
        Boolean expected  = service.makeInputAgainOrExit();
        assertEquals(expected, EXPECTED_POSITIVE);
        sc.close();
    }

    @Test
    public void testMakeInputAgainOrExitNegativeeAnsver() {
        Scanner sc = new Scanner(NO_ANSWER);
        service.setScanner(sc);
        Mockito.when(dataRepository.getMetaDataMap()).thenReturn(mapMeatData);
        Boolean expected  = service.makeInputAgainOrExit();
        assertEquals(expected, EXPECTED_NEGATIVE);
        sc.close();
    }

}
