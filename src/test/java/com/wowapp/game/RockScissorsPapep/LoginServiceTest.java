package com.wowapp.game.RockScissorsPapep;

import com.wowapp.game.exeption.PasswordIncorrectExeption;
import com.wowapp.game.exeption.UserNotFoundExeption;
import com.wowapp.game.dto.User;
import com.wowapp.game.service.ILoginService;
import com.wowapp.game.service.IUserService;
import com.wowapp.game.service.impl.LoginServiceImpl;
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
public class LoginServiceTest {
    @Mock
    private IUserService userService;
    @InjectMocks
    private ILoginService service = new LoginServiceImpl();
    private static final String LOGIN = "test1";
    private static final String PASSWORD = "passw1";
    private static final String WRONG_PASSWORD = "wrongpassord";
    private static final Integer ZERO_VALUE = 0;

    @Test
    public void testCheckUserLoginPasswordShouldReturnTrueResult() {

        User user = getUserInfo();
        Mockito.when(userService.findByLogin(Mockito.eq(LOGIN))).thenReturn(user);
        Boolean expectedResult = service.checkUserLoginPassword(LOGIN, PASSWORD);
        Mockito.verify(userService, Mockito.times(1)).findByLogin(Mockito.eq(LOGIN));
        assertEquals(expectedResult, true);

    }

    @Test(expected = UserNotFoundExeption.class)
    public void testCheckUserLoginPasswordShouldReturnUserNotFoundExeption() {
        Mockito.when(userService.findByLogin(Mockito.eq(LOGIN))).thenReturn(null);
        service.checkUserLoginPassword(LOGIN, PASSWORD);
        Mockito.verify(userService, Mockito.times(1)).findByLogin(Mockito.eq(LOGIN));
    }

    @Test(expected = PasswordIncorrectExeption.class)
    public void testCheckUserLoginPasswordShouldReturnPasswordIncorrectExeption() {
        User user = getUserInfo();
        Mockito.when(userService.findByLogin(Mockito.eq(LOGIN))).thenReturn(user);
        service.checkUserLoginPassword(LOGIN, WRONG_PASSWORD);
        Mockito.verify(userService, Mockito.times(1)).findByLogin(Mockito.eq(LOGIN));
    }

    private User getUserInfo() {
        return new User(ZERO_VALUE, LOGIN, PASSWORD, ZERO_VALUE, ZERO_VALUE, ZERO_VALUE);
    }
}
