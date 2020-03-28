package com.wowapp.game.RockScissorsPapep;

import com.wowapp.game.dto.User;
import com.wowapp.game.repository.IGameDataSourceRepository;
import com.wowapp.game.service.IUserService;
import com.wowapp.game.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by pek on 2/22/2019.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest()
public class UserServiceTest {
    @Mock
    private IGameDataSourceRepository dataRepository;
    @InjectMocks
    private IUserService service = new UserServiceImpl();
    private static final String LOGIN_ONE = "userName1";
    private static final String LOGIN_TWO = "userName2";
    private static final String PASSWORD_ONE = "password1";
    private static final String PASSWORD_TWO = "password2";

    private static final Integer ZERO_VALUE = 0;
    private static final Integer UPDATED_VALUE = 1;
    @Test
    public void testValidateUserNamePassword() {
        Map<String, User> mapUser = new HashMap<>();

        User userOne = getUserInfoOne();
        User userTwo = getUserInfoTwo();
        mapUser.put(LOGIN_ONE, userOne);
        mapUser.put(LOGIN_TWO, userTwo);

        Map<String, User> map = new HashMap<>();
        Mockito.when(dataRepository.getDataSourceUserMap()).thenReturn(mapUser);
        User expectedResult = service.findByLogin(LOGIN_ONE);

        assertEquals(expectedResult, userOne);

    }

    @Test
    public void testSaveUser() {
        Map<String, User> mapUser = new HashMap<>();

        User userOne = getUserInfoOne();
        User userTwo = getUserInfoTwo();

        mapUser.put(LOGIN_TWO, userTwo);

        Mockito.when(dataRepository.getDataSourceUserMap()).thenReturn(mapUser);
        service.save(userOne);

        assertEquals(dataRepository.getDataSourceUserMap().get(LOGIN_ONE), userOne);
        assertEquals(dataRepository.getDataSourceUserMap().size(),2);
    }

    @Test
    public void testUpdateUser() {
        Map<String, User> mapUser = new HashMap<>();

        User userOne = getUserInfoOne();
        User userTwo = getUserInfoTwo();
        mapUser.put(LOGIN_ONE, userOne);
        mapUser.put(LOGIN_TWO, userTwo);
        User updaterUser =  getUpdatedUserInfo();

        Mockito.when(dataRepository.getDataSourceUserMap()).thenReturn(mapUser);
        service.update(updaterUser);

        assertEquals(dataRepository.getDataSourceUserMap().get(LOGIN_ONE), updaterUser);
        assertEquals(dataRepository.getDataSourceUserMap().size(),2);
    }
    private User getUserInfoOne() {
        return new User(ZERO_VALUE, LOGIN_ONE, PASSWORD_ONE, ZERO_VALUE, ZERO_VALUE, ZERO_VALUE);
    }

    private User getUserInfoTwo() {
        return new User(ZERO_VALUE, LOGIN_TWO, PASSWORD_TWO, ZERO_VALUE, ZERO_VALUE, ZERO_VALUE);
    }

    private User getUpdatedUserInfo() {
        return new User(ZERO_VALUE, LOGIN_ONE, PASSWORD_ONE, UPDATED_VALUE, UPDATED_VALUE, UPDATED_VALUE);
    }
}
