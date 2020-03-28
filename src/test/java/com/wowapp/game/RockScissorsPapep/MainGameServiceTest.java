package com.wowapp.game.RockScissorsPapep;

import com.wowapp.game.exeption.UserNotFoundExeption;
import com.wowapp.game.dto.User;
import com.wowapp.game.enums.PlayAttribute;
import com.wowapp.game.enums.UserAction;
import com.wowapp.game.service.*;
import com.wowapp.game.service.impl.MainGameServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

/**
 * Created by pek on 2/22/2019.
 */

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest()
public class MainGameServiceTest {

    @Mock
    private ILoginService loginService;
    @Mock
    private IRegistrationService registrationService;
    @Mock
    private IScreenInterface screenInterface;

    private static final Integer ROUND = 1;
    @InjectMocks
    private IMainGameService service = new MainGameServiceImpl();

    private static final UserAction LOGIN_ACTION = UserAction.LOGIN;
    private static final UserAction REGISTER_ACTION = UserAction.REGISTER;
    private static final String LOGIN = "test1";
    private static final String PASSWORD = "passw1";
    private static final Integer ZERO_VALUE = 0;

    @Test
    public void testloginOrRegisterUserShouldLoginUserAndReturnExistsUser() {
        User newUser = getUserInfo();
        Mockito.when(screenInterface.userLoginOrRegistrationStep()).thenReturn(LOGIN_ACTION);
        Mockito.when(screenInterface.getLoginUserFromInput()).thenReturn(LOGIN);
        Mockito.when(screenInterface.getPasswordUserFromInput()).thenReturn(PASSWORD);
        Mockito.when(loginService.checkUserLoginPassword(Mockito.eq(LOGIN), Mockito.eq(PASSWORD))).thenReturn(true);
        Mockito.when(loginService.getUserData(Mockito.eq(LOGIN))).thenReturn(newUser);
        User expectedUser = service.loginOrRegisterUser();
        Mockito.verify(loginService, Mockito.times(1))
                .checkUserLoginPassword(Mockito.eq(LOGIN), Mockito.eq(PASSWORD));

        assertEquals(expectedUser, newUser);
    }
    @Test
    public void testloginOrRegisterUserShouldReturnUserNotFoundExeption() {
        User newUser = getUserInfo();
        Mockito.when(screenInterface.userLoginOrRegistrationStep()).thenReturn(LOGIN_ACTION) ;
        Mockito.when(screenInterface.getLoginUserFromInput()).thenReturn(LOGIN).thenReturn(LOGIN)  ;
        Mockito.when(screenInterface.getPasswordUserFromInput()).thenReturn(PASSWORD).thenReturn(PASSWORD) ;
        Mockito.when(loginService.checkUserLoginPassword(Mockito.eq(LOGIN), Mockito.eq(PASSWORD)))
                .thenThrow( (UserNotFoundExeption.class)).thenReturn(true);
        Mockito.when(loginService.getUserData(Mockito.eq(LOGIN))).thenReturn(newUser);
        User expectedUser = service.loginOrRegisterUser();
        Mockito.verify(loginService, Mockito.times(2))
                .checkUserLoginPassword(Mockito.eq(LOGIN), Mockito.eq(PASSWORD));

        assertEquals(expectedUser, newUser);
    }

    @Test
      public void testloginOrRegisterUserShouldRegisterUser () {
        User newUser = getUserInfo();
        Mockito.when(screenInterface.userLoginOrRegistrationStep()).thenReturn(REGISTER_ACTION);
        Mockito.when(screenInterface.getLoginUserFromInput()).thenReturn(LOGIN);
        Mockito.when(registrationService.checkUserExists(Mockito.eq(LOGIN))).thenReturn(false);
        Mockito.when(screenInterface.getPasswordUserFromInput()).thenReturn(PASSWORD);
        Mockito.when(registrationService.
                validateUserNamePassword(Mockito.eq(LOGIN), Mockito.eq(PASSWORD))).thenReturn(true);

        Mockito.when(loginService.getUserData(Mockito.eq(LOGIN))).thenReturn(newUser);

        User expectedUser = service.loginOrRegisterUser();
        Mockito.verify(registrationService, Mockito.times(1))
                .checkUserExists(Mockito.eq(LOGIN));
        Mockito.verify(registrationService, Mockito.times(1))
                .validateUserNamePassword(Mockito.eq(LOGIN), Mockito.eq(PASSWORD));
        Mockito.verify(registrationService, Mockito.times(1))
                .registerUser(Mockito.eq(LOGIN), Mockito.eq(PASSWORD));
        assertEquals(expectedUser, newUser);
    }

    @Test
    public void testloginOrRegisterUserShouldRegisterUserForSecondTimeInput () {
        User newUser = getUserInfo();
        Mockito.when(screenInterface.userLoginOrRegistrationStep()).thenReturn(REGISTER_ACTION);
        Mockito.when(screenInterface.getLoginUserFromInput()).thenReturn(LOGIN).thenReturn(LOGIN);
        Mockito.when(registrationService.checkUserExists(Mockito.eq(LOGIN))).thenReturn(false).thenReturn(false);
        Mockito.when(screenInterface.getPasswordUserFromInput()).thenReturn(PASSWORD).thenReturn(PASSWORD);
        Mockito.when(registrationService.
                validateUserNamePassword(Mockito.eq(LOGIN), Mockito.eq(PASSWORD))).thenReturn(false).thenReturn(true);

        Mockito.when(loginService.getUserData(Mockito.eq(LOGIN))).thenReturn(newUser);

        User expectedUser = service.loginOrRegisterUser();
        Mockito.verify(registrationService, Mockito.times(2))
                .checkUserExists(Mockito.eq(LOGIN));
        Mockito.verify(registrationService, Mockito.times(2))
                .validateUserNamePassword(Mockito.eq(LOGIN), Mockito.eq(PASSWORD));
        Mockito.verify(registrationService, Mockito.times(1))
                .registerUser(Mockito.eq(LOGIN), Mockito.eq(PASSWORD));
        assertEquals(expectedUser, newUser);
    }

    @Test
    public void testStartPlayManualRound () {
        Mockito.when(screenInterface.getUserStepFromScreen()).thenReturn(PlayAttribute.PAPER);
        service.startPlayManualRound(ROUND) ;
        Mockito.verify(screenInterface, Mockito.times(1))
                .getUserStepFromScreen();
    }

    @Test
    public void testStartPlayAuthomaticRound() {
        Mockito.when(screenInterface.getUserStepFromScreen()).thenReturn(PlayAttribute.PAPER);
        service.startPlayAuthomaticRound(ROUND) ;
        Mockito.verify(screenInterface, Mockito.times(0))
                .getUserStepFromScreen();
    }

    private User getUserInfo() {
        return new User(ZERO_VALUE, LOGIN, PASSWORD, ZERO_VALUE, ZERO_VALUE, ZERO_VALUE);
    }

}
