package com.wowapp.game.RockScissorsPapep;

import com.wowapp.game.dto.User;
import com.wowapp.game.service.IRegistrationService;
import com.wowapp.game.service.IUserService;
import com.wowapp.game.service.impl.RegistrationServiceImpl;
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
public class RegistrationServiceTest {
    @Mock
    private IUserService userService;
    @InjectMocks
    private IRegistrationService service = new RegistrationServiceImpl();
    private static final String LOGIN = "testtest1";
    private static final String PASSWORD = "password1";

    private static final Integer ZERO_VALUE = 0;

    @Test
    public void testValidateUserNamePassword() {
        User user = getUserInfo();
        Mockito.when(userService.findByLogin(Mockito.eq(LOGIN))).thenReturn(user);
        Boolean expectedResult = service.validateUserNamePassword(LOGIN, PASSWORD);
        assertEquals(expectedResult, true);
    }

    @Test
    public void testCheckUserExists() {
        User user = getUserInfo();
        Mockito.when(userService.findByLogin(Mockito.eq(LOGIN))).thenReturn(user);
        Boolean expectedResult = service.checkUserExists(LOGIN);
        assertEquals(expectedResult, true);
    }

    @Test
    public void testCheckUserNotExists() {
        User user = getUserInfo();
        Mockito.when(userService.findByLogin(Mockito.eq(LOGIN))).thenReturn(null);
        Boolean expectedResult = service.checkUserExists(LOGIN);
        assertEquals(expectedResult, false);
    }

    private User getUserInfo() {
        return new User(ZERO_VALUE, LOGIN, PASSWORD, ZERO_VALUE, ZERO_VALUE, ZERO_VALUE);
    }

}