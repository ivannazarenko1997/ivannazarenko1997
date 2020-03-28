package com.wowapp.game.RockScissorsPapep;

import com.wowapp.game.repository.impl.GameDataSourceRepositoryImpl;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by pek on 2/23/2019.
 */
public class GameDataSourceRepositoryTest {
    @Test
    public void checkThatMapIsFullOnStartUp() {
        GameDataSourceRepositoryImpl tester = new GameDataSourceRepositoryImpl(); // MyClass is tested
        tester.initializeDataSourceInfo();
        assertNotNull(tester.getDataSourceUserMap());
        assertNotNull(tester.getMetaDataMap());


    }
}
